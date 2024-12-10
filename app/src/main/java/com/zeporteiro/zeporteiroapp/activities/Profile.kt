package com.zeporteiro.zeporteiroapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zeporteiro.zeporteiroapp.R
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme
import com.zeporteiro.zeporteiroapp.utils.NavigationRoutes
import com.zeporteiro.zeporteiroapp.viewModel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    val viewModel: ProfileViewModel = viewModel()
    val estado by viewModel.estado.collectAsState()
    val scrollState = rememberScrollState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<ProfileViewModel.Campo?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meu Perfil",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF294B29)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color(0xFF294B29)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Sair",
                            tint = Color(0xFF294B29)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(scrollState)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFF294B29), CircleShape)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = estado.nomeMorador.take(2).uppercase(),
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Campos do perfil
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ProfileItemField(
                    label = "Nome Completo",
                    value = estado.nomeMorador,
                    onEdit = { showEditDialog = ProfileViewModel.Campo.NOME }
                )

                ProfileItemField(
                    label = "E-mail cadastrado",
                    value = estado.email,
                    onEdit = { showEditDialog = ProfileViewModel.Campo.EMAIL }
                )

                ProfileItemField(
                    label = "Telefone celular",
                    value = estado.telefoneCelular,
                    onEdit = { showEditDialog = ProfileViewModel.Campo.CELULAR }
                )

                ProfileItemField(
                    label = "Número do apartamento",
                    value = estado.numeroApartamento,
                    onEdit = { showEditDialog = ProfileViewModel.Campo.APARTAMENTO }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Diálogo de Logout
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Sair da conta", fontWeight = FontWeight.Bold) },
                text = { Text("Tem certeza que deseja sair da sua conta?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showLogoutDialog = false
                            // Navega para login e limpa a pilha de navegação
                            navController.navigate(NavigationRoutes.LOGIN) {
                                // Limpa toda a pilha de navegação
                                popUpTo(NavigationRoutes.WELCOME) { inclusive = true }
                                // Evita múltiplas cópias da tela de login na pilha
                                launchSingleTop = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF294B29)
                        )
                    ) {
                        Text("Sim, sair")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Diálogo de Edição
        showEditDialog?.let { campo ->
            var novoValor by remember {
                mutableStateOf(
                    when (campo) {
                        ProfileViewModel.Campo.NOME -> estado.nomeMorador
                        ProfileViewModel.Campo.EMAIL -> estado.email
                        ProfileViewModel.Campo.CELULAR -> estado.telefoneCelular
                        ProfileViewModel.Campo.APARTAMENTO -> estado.numeroApartamento
                    }
                )
            }

            AlertDialog(
                onDismissRequest = { showEditDialog = null },
                title = {
                    Text(
                        text = "Editar ${
                            when (campo) {
                                ProfileViewModel.Campo.NOME -> "Nome"
                                ProfileViewModel.Campo.EMAIL -> "E-mail"
                                ProfileViewModel.Campo.CELULAR -> "Celular"
                                ProfileViewModel.Campo.APARTAMENTO -> "Apartamento"
                            }
                        }"
                    )
                },
                text = {
                    OutlinedTextField(
                        value = novoValor,
                        onValueChange = { novoValor = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.atualizarCampo(campo, novoValor)
                            showEditDialog = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF294B29)
                        )
                    ) {
                        Text("Salvar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditDialog = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileItemField(
    label: String,
    value: String,
    onEdit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = Color(0xFF71727A),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

            IconButton(
                onClick = onEdit,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar $label",
                    tint = Color(0xFF294B29)
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = value,
                    color = Color(0xFF1F2024),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

//class Profile : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            ZePorteiroAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ProfileScreen(
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun ProfileScreen(modifier: Modifier = Modifier, navController: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF5F5F5))
//            .padding(16.dp)
//    ) {
//        Header5()
//        Spacer(modifier = Modifier)
//        ProfileField()
//    }
//}
//
//@Composable
//fun Header5() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 40.dp, bottom = 10.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = "Meu Perfil",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF294B29),
//        )
//
//    }
//}
//
//@Composable
//fun ProfileField() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 30.dp, start = 13.dp, end = 13.dp)
//    ) {
//        ProfileItemField(
//            label = "Nome Completo",
//            value = "Nome do morador"
//        )
//        ProfileItemField(
//            label = "E-mail cadastrado",
//            value = "exemplo@email.com"
//        )
//        ProfileItemField(
//            label = "Telefone celular",
//            value = "(11) 98746-1847"
//        )
//        ProfileItemField(
//            label = "Telefone fixo",
//            value = "-"
//        )
//        ProfileItemField(
//            label = "Número do apartamento",
//            value = "101"
//        )
//    }
//}
//
//@Composable
//fun ProfileItemField(
//    label: String,
//    value: String
//) {
//    Text(
//        text = label,
//        color = Color.Black,
//        fontWeight = FontWeight.Bold,
//        fontSize = 14.sp,
//        modifier = Modifier.padding(bottom = 6.dp)
//    )
//    Column(
//        modifier = Modifier
//            .padding(bottom = 22.dp)
//            .clip(shape = RoundedCornerShape(9.dp))
//            .fillMaxWidth()
//            .background(
//                color = Color(0xFFEFEFEF),
//                shape = RoundedCornerShape(9.dp)
//            )
//            .padding(vertical = 19.dp, horizontal = 25.dp)
//    ) {
//        Text(
//            text = value,
//            color = Color(0xFF000000),
//            fontSize = 14.sp,
//        )
//    }
//}

//@Composable
//fun BottomNavigationBar3() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 65.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        NavigationItem3(iconId = R.mipmap.icon_inicial_screen, label = "Página Inicial")
//        NavigationItem3(iconId = R.mipmap.icon_package_gray, label = "Encomendas")
//        NavigationItem3(iconId = R.mipmap.icon_profile_black, label = "Perfil")
//    }
//}
//
//@Composable
//fun NavigationItem3(iconId: Int, label: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = iconId),
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
//fun DefaultPreview() {
//    ZePorteiroAppTheme {
//        ProfileScreen()
//    }
//}
