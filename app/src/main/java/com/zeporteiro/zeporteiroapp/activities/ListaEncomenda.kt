package com.zeporteiro.zeporteiroapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.zeporteiro.zeporteiroapp.R
import com.zeporteiro.zeporteiroapp.data.Entrega
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme
import com.zeporteiro.zeporteiroapp.viewModel.ListaEncomendaViewModel
import com.zeporteiro.zeporteiroapp.viewModel.SignUpViewModel
import org.koin.java.KoinJavaComponent.inject

//class ListaEncomenda : ComponentActivity() {
//    val viewModel: ListaEncomendaViewModel by inject(ListaEncomendaViewModel::class.java)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            ZePorteiroAppTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = Color(0xFFF5F5F5)
//                ) {
//                    Scaffold(
//                        bottomBar = { BottomNavigationBar() }
//                    ) { innerPadding ->
//                        ListaEncomendasScreen(
//                            modifier = Modifier.padding(innerPadding),
//                            viewModel = viewModel
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun ListaEncomendasScreen(
    modifier: Modifier = Modifier,
    viewModel: ListaEncomendaViewModel,
    navController: NavController
) {
//    val entregas = viewModel.entregas.value
//    val entregas by remember { viewModel.entregas }
//    val entregas by remember(viewModel) {
//        snapshotFlow { viewModel.entregas.value }
//    }.collectAsState(initial = emptyList())

//    var entregas by remember { mutableStateOf<List<Entrega>>(emptyList()) }

    val entregas by viewModel.entregas.collectAsStateWithLifecycle()

    val isLoading = viewModel.isLoading.collectAsState().value
    val error = viewModel.error.collectAsState().value

//    LaunchedEffect(viewModel) {
//        viewModel.entregas.collect { novaLista ->
//            entregas = novaLista
//        }
//    }

//    LaunchedEffect(Unit) {
//        viewModel.carregarEntregas()
//    }

    // Log para debug
    LaunchedEffect(entregas.size) {
        println("Debug - Quantidade de entregas: ${entregas.size}")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .systemBarsPadding()
    ) {
        Header3()
        Search()

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF294B29)
                    )
                }
                error != null -> {
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                entregas.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhuma entrega encontrada",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = 8.dp,
                            bottom = 80.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = entregas,
                            key = { it.id ?: 0 }
                        ) { entrega ->
                            EntregaItem(
                                entrega = entrega,
                                onConfirmarClick = {
                                    entrega.id?.let { id -> viewModel.confirmarRecebimento(id) }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EntregaItem(
    entrega: Entrega,
    onConfirmarClick: () -> Unit
) {
    val isPendente = !entrega.recebido

    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(
                color = if (isPendente) Color(0xFF294B29) else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 23.dp, horizontal = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entrega.tipoEntrega ?: "Encomenda sem tipo",
                    color = if (isPendente) Color.White else Color(0xFF0D1B34),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                entrega.porteiro?.let { porteiro ->
                    Text(
                        text = "Porteiro ${porteiro.nome ?: ""}",
                        color = if (isPendente) Color(0xFFB4B7BF) else Color(0xFF8696BB),
                        fontSize = 14.sp
                    )
                }
            }

            if (isPendente) {
                Text(
                    text = "Confirmar Entrega",
                    color = Color(0xFF294B29),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .clickable { onConfirmarClick() }
                        .padding(vertical = 9.dp, horizontal = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(
                    id = if (isPendente) R.mipmap.icon_calender_white else R.mipmap.icon_calendar
                ),
                contentDescription = "Recebimento Porteiro",
                modifier = Modifier
                    .padding(end = 9.dp)
                    .size(16.dp)
            )
            Text(
                text = "Recebido pelo porteiro: ${entrega.dataRecebimentoPorteiro ?: ""}",
                color = if (isPendente) Color.White else Color(0xFF6B6E82),
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 21.dp)
            )
        }

        if (!isPendente && entrega.dataRecebimentoMorador != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(
                        id = R.mipmap.icon_calendar
                    ),
                    contentDescription = "Recebimento Morador",
                    modifier = Modifier
                        .padding(end = 9.dp)
                        .size(16.dp)
                )
                Text(
                    text = "Recebido pelo morador: ${entrega.dataRecebimentoMorador}",
                    color = Color(0xFF6B6E82),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun Header3() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Minhas\nEncomendas",
            color = Color(0xFF294B29),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Configurações",
                tint = Color(0xFF294B29),
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun Search() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFEFEFEF))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.icon_search),
                    contentDescription = "Pesquisa",
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 10.dp)
                )
                Text(
                    "Pesquisar",
                    color = Color(0xFF1F2024),
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterButton(
                icon = R.mipmap.icon_order,
                text = "Ordenar"
            )
            FilterButton(
                icon = R.mipmap.icon_filter,
                text = "Filtrar",
                badge = "2"
            )
        }
    }
}

@Composable
fun FilterButton(
    icon: Int,
    text: String,
    badge: String? = null
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFFC5C6CC),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = text,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = Color(0xFF1F2024),
            fontSize = 12.sp
        )
        if (badge != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(21.dp)
                    .background(
                        color = Color(0xFF627B4D),
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = badge,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        } else {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.mipmap.icon_arrow_down),
                contentDescription = "Seta para baixo",
                modifier = Modifier.size(10.dp)
            )
        }
    }
}

//@Composable
//fun ConfirmarEncomenda() {
//    Column(
//        modifier = Modifier
//            .padding(bottom = 20.dp, start = 14.dp, end = 14.dp,)
//            .clip(shape = RoundedCornerShape(12.dp))
//            .fillMaxWidth()
//            .background(
//                color = Color(0xFF294B29),
//                shape = RoundedCornerShape(12.dp)
//            )
//            .padding(vertical = 23.dp, horizontal = 20.dp,)
//    ){
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .padding(bottom = 10.dp,)
//                .fillMaxWidth()
//        ){
//            Column(
//                modifier = Modifier
//                    .width(142.dp)
//            ){
//                Text("Encomenda #232",
//                    color = Color(0xFFFFFFFF),
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.ExtraBold,
//                    modifier = Modifier
//                        .padding(bottom = 6.dp,)
//                )
//                Text("Porteiro José",
//                    color = Color(0xFFB4B7BF),
//                    fontSize = 14.sp,
//                    modifier = Modifier
//                        .padding(start = 1.dp,)
//                )
//            }
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .border(
//                        width = 1.dp,
//                        color = Color(0xFFFFFFFF),
//                        shape = RoundedCornerShape(12.dp)
//                    )
//                    .clip(shape = RoundedCornerShape(12.dp))
//                    .width(139.dp)
//                    .background(
//                        color = Color(0xFFFFFFFF),
//                    )
//                    .padding(vertical = 9.dp,)
//            ){
//                Text("Confirmar Entrega",
//                    color = Color(0xFF294B29),
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//
//        Column(
//            modifier = Modifier
//                .padding(bottom = 16.dp,)
//                .height(1.dp)
//                .fillMaxWidth()
//        ){
//        }
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//        ){
//            Image(
//                painter = painterResource(id = R.mipmap.icon_calender_white),
//                contentDescription = "Calendário",
//                modifier = Modifier
//                    .padding(end = 9.dp,)
//                    .width(16.dp)
//                    .height(16.dp)
//            )
//            Text("07/08/2024",
//                color = Color(0xFFFFFFFF),
//                fontSize = 12.sp,
//                modifier = Modifier
//                    .padding(end = 21.dp,)
//            )
//            Image(
//                painter = painterResource(id = R.mipmap.icon_clock_white),
//                contentDescription = "Horário",
//                modifier = Modifier
//                    .padding(end = 9.dp,)
//                    .width(16.dp)
//                    .height(16.dp)
//            )
//            Text("11:45:54",
//                color = Color(0xFFFFFFFF),
//                fontSize = 12.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//        }
//    }
//    Column(
//        modifier = Modifier
//            .padding(bottom = 15.dp, start = 14.dp, end = 14.dp,)
//            .clip(shape = RoundedCornerShape(12.dp))
//            .fillMaxWidth()
//            .background(
//                color = Color(0xFFFFFFFF),
//                shape = RoundedCornerShape(12.dp)
//            )
//            .padding(vertical = 23.dp, horizontal = 16.dp,)
//    ){
//        Text("Encomenda #221",
//            color = Color(0xFF0D1B34),
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(bottom = 6.dp,)
//        )
//        Text("Porteiro Leandro",
//            color = Color(0xFF8696BB),
//            fontSize = 14.sp,
//            modifier = Modifier
//                .padding(bottom = 15.dp,)
//        )
//        Column(
//            modifier = Modifier
//                .height(1.dp)
//                .fillMaxWidth()
//        ){
//        }
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//        ){
//            Image(
//                painter = painterResource(id = R.mipmap.icon_calendar),
//                contentDescription = "Calendário",
//                modifier = Modifier
//                    .padding(end = 9.dp,)
//                    .width(16.dp)
//                    .height(16.dp)
//            )
//            Text("06/08/2024",
//                color = Color(0xFF6B6E82),
//                fontSize = 12.sp,
//                modifier = Modifier
//                    .padding(end = 22.dp,)
//            )
//            Image(
//                painter = painterResource(id = R.mipmap.icon_clock),
//                contentDescription = "Horário",
//                modifier = Modifier
//                    .padding(end = 9.dp,)
//                    .width(16.dp)
//                    .height(16.dp)
//            )
//            Text("14:42:08",
//                color = Color(0xFF6B6E82),
//                fontSize = 12.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//        }
//    }
//        Column(
//            modifier = Modifier
//                .padding(bottom = 15.dp, start = 14.dp, end = 14.dp,)
//                .clip(shape = RoundedCornerShape(12.dp))
//                .fillMaxWidth()
//                .background(
//                    color = Color(0xFFFFFFFF),
//                    shape = RoundedCornerShape(12.dp)
//                )
//                .padding(vertical = 23.dp, horizontal = 16.dp,)
//        ){
//            Text("Encomenda #201",
//                color = Color(0xFF0D1B34),
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(bottom = 6.dp,)
//            )
//            Text("Porteiro Leandro",
//                color = Color(0xFF8696BB),
//                fontSize = 14.sp,
//
//            )
//        Column(
//            modifier = Modifier
//                .padding(bottom = 15.dp,)
//                .height(1.dp)
//                .fillMaxWidth()
//        ){
//        }
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//        ){
//            Image(
//                painter = painterResource(id = R.mipmap.icon_calendar),
//                contentDescription = "Calendário",
//                modifier = Modifier
//                    .padding(end = 9.dp,)
//                    .width(16.dp)
//                    .height(16.dp)
//            )
//            Text("06/08/2024",
//                color = Color(0xFF6B6E82),
//                fontSize = 12.sp,
//                modifier = Modifier
//                    .padding(end = 22.dp,)
//            )
//            Image(
//                painter = painterResource(id = R.mipmap.icon_clock),
//                contentDescription = "Horário",
//                modifier = Modifier
//                    .padding(end = 9.dp,)
//                    .width(16.dp)
//                    .height(16.dp)
//            )
//            Text("14:42:08",
//                color = Color(0xFF6B6E82),
//                fontSize = 12.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//        }
//    }
//}


//@Composable
//fun BottomNavigationBar(
//    navController: NavController,
//    currentRoute: String?
//) {
//    val selectedItem = remember { mutableIntStateOf(0) }
//
//    NavigationBar(
//        containerColor = Color.White,
//        tonalElevation = 8.dp
//    ) {
//        NavigationBarItem(
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Home,
//                    contentDescription = "Página Inicial",
//                    modifier = Modifier.size(24.dp)
//                )
//            },
//            label = {
//                Text(
//                    text = "Início",
//                    fontSize = 12.sp
//                )
//            },
//            selected = currentRoute == NavigationRoutes.HOME,
//            onClick = {
//                selectedItem.intValue = 0
//                navController.navigate(NavigationRoutes.HOME) {
//                    popUpTo(NavigationRoutes.HOME) {
//                        saveState = true
//                    }
//                    launchSingleTop = true
//                    restoreState = true
//                }
//            }
//        )
//
//        NavigationBarItem(
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Archive, // Trocado de Inventory para Archive
//                    contentDescription = "Encomendas",
//                    modifier = Modifier.size(24.dp)
//                )
//            },
//            label = {
//                Text(
//                    text = "Encomendas",
//                    fontSize = 12.sp
//                )
//            },
//            selected = currentRoute == NavigationRoutes.LISTA_ENCOMENDAS,
//            onClick = {
//                selectedItem.intValue = 1
//                navController.navigate(NavigationRoutes.LISTA_ENCOMENDAS) {
//                    popUpTo(NavigationRoutes.HOME) {
//                        saveState = true
//                    }
//                    launchSingleTop = true
//                    restoreState = true
//                }
//            }
//        )
//
//        NavigationBarItem(
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Person,
//                    contentDescription = "Perfil",
//                    modifier = Modifier.size(24.dp)
//                )
//            },
//            label = {
//                Text(
//                    text = "Perfil",
//                    fontSize = 12.sp
//                )
//            },
//            selected = currentRoute == NavigationRoutes.PROFILE,
//            onClick = {
//                selectedItem.intValue = 2
//                navController.navigate(NavigationRoutes.PROFILE) {
//                    popUpTo(NavigationRoutes.HOME) {
//                        saveState = true
//                    }
//                    launchSingleTop = true
//                    restoreState = true
//                }
//            }
//        )
//    }
//}
//
//@Composable
//fun NavigationItem2(iconId: Int, label: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = iconId),
//            contentDescription = label,
//            modifier = Modifier.size(20.dp)
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = label,
//            fontSize = 10.sp,
//            color = Color(0xFF71727A)
//        )
//    }
//}



