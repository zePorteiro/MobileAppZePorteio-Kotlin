package com.zeporteiro.zeporteiroapp.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zeporteiro.zeporteiroapp.R
import com.zeporteiro.zeporteiroapp.utils.NavigationRoutes

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Header()
        NovasAtividades()
        Atalhos(navController)
    }
}


@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Olá, Usuário!",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF294B29)
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
fun NovasAtividades() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 45.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFB5B7BF), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Novas atividades",
            fontSize = 10.sp,
            color = Color(0xFF1F2024)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Confirmar nova encomenda",
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun Atalhos(navController: NavController) {
    Text(
        text = "Acesso rápido",
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 66.dp, bottom = 12.dp)
    )
    AtalhosItem(
        icon = R.mipmap.icon_confirm,
        title = "Confirmar Entrega",
        description = "Confirme a retirada da sua encomenda"
    ) {
        navController.navigate(NavigationRoutes.LISTA_ENCOMENDAS)
    }
    AtalhosItem(
        icon = R.mipmap.icon_clipboard,
        title = "Minhas Encomendas",
        description = "Veja a lista de suas encomendas"
    ) {
        navController.navigate(NavigationRoutes.LISTA_ENCOMENDAS)
    }
    AtalhosItem(
        icon = R.mipmap.icon_person,
        title = "Meu Perfil",
        description = "Veja as informações do seu perfil"
    ) {
        navController.navigate(NavigationRoutes.PROFILE)
    }
}

@Composable
fun AtalhosItem(
    icon: Int,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(55.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF294B29)
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(0xFF71727A)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.mipmap.icon_arrow_right),
            contentDescription = "Seta para o lado direito",
            modifier = Modifier.size(25.dp)
        )
    }
}

//@Composable
//fun BarraDeNavegacao() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White)
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        NavegacaoItem(
//            icon = R.mipmap.icon_inicial_screen,
//            label = "Página Inicial"
//        )
//        NavegacaoItem(
//            icon = R.mipmap.icon_package,
//            label = "Encomendas"
//        )
//        NavegacaoItem(
//            icon = R.mipmap.icon_profile,
//            label = "Perfil"
//        )
//    }
//}
//
//@Composable
//fun NavegacaoItem(icon: Int, label: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = icon),
//            contentDescription = null,
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


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview2() {
//    ZePorteiroAppTheme {
//        HomeScreen()
//    }
//}