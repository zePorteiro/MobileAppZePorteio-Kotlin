package com.zeporteiro.zeporteiroapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zeporteiro.zeporteiroapp.R
import com.zeporteiro.zeporteiroapp.components.BottomNavigationBar
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme
import com.zeporteiro.zeporteiroapp.utils.NavigationRoutes
import com.zeporteiro.zeporteiroapp.viewModel.ListaEncomendaViewModel
import org.koin.android.ext.android.inject

class WelcomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZePorteiroAppTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F5F5)
                ) {
                    Scaffold(
                        bottomBar = {
                            // Mostra a barra apenas nas telas principais
                            when (currentRoute) {
                                NavigationRoutes.HOME,
                                NavigationRoutes.LISTA_ENCOMENDAS,
                                NavigationRoutes.PROFILE -> {
                                    BottomNavigationBar(
                                        navController = navController,
                                        currentRoute = currentRoute
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = NavigationRoutes.WELCOME,
                            modifier = if (currentRoute in listOf(NavigationRoutes.HOME, NavigationRoutes.LISTA_ENCOMENDAS, NavigationRoutes.PROFILE)) {
                                Modifier.padding(innerPadding)
                            } else {
                                Modifier
                            }
                        ) {
                            // Rotas de autenticação
                            composable(NavigationRoutes.WELCOME) {
                                WelcomeScreen(navController = navController)
                            }
                            composable(NavigationRoutes.LOGIN) {
                                LoginScreen(
                                    navController = navController,
                                    onLoginSuccess = {
                                        navController.navigate(NavigationRoutes.HOME) {
                                            popUpTo(NavigationRoutes.WELCOME) { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable(NavigationRoutes.SIGNUP) {
                                SignUpScreen(navController = navController)
                            }
                            composable(NavigationRoutes.SIGNUP2) {
                                CadastroScreen(navController = navController)
                            }

                            // Rotas principais (com barra de navegação)
                            composable(NavigationRoutes.HOME) {
                                HomeScreen(navController = navController)
                            }
                            composable(NavigationRoutes.LISTA_ENCOMENDAS) {
                                val viewModel: ListaEncomendaViewModel by inject()
                                ListaEncomendasScreen(
                                    viewModel = viewModel,
                                    navController = navController
                                )
                            }
                            composable(NavigationRoutes.PROFILE) {
                                ProfileScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, navController: NavController) {

    val fontProvider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val poppins = GoogleFont("Poppins")
    val inter = GoogleFont("Inter")

    val poppinsFamily = FontFamily(
        Font(googleFont = poppins, fontProvider = fontProvider, weight = FontWeight.Light),
        Font(googleFont = poppins, fontProvider = fontProvider, weight = FontWeight.Normal),
        Font(googleFont = poppins, fontProvider = fontProvider, weight = FontWeight.Medium),
        Font(googleFont = poppins, fontProvider = fontProvider, weight = FontWeight.SemiBold),
        Font(googleFont = poppins, fontProvider = fontProvider, weight = FontWeight.Bold),
        Font(googleFont = poppins, fontProvider = fontProvider, weight = FontWeight.ExtraBold),
        Font(googleFont = poppins, fontProvider = fontProvider, weight = FontWeight.Black)
    )

    val interFamily = FontFamily(
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Light),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Normal),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Medium),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.SemiBold),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Bold),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.ExtraBold),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Black)
    )

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(bottom = 30.dp)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.welcome_image),
            contentDescription = "Imagem decorativa de uma encomenda",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .padding(bottom = 50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color(0xFF294B29), CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color(0xFF627B4E), CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color(0xFF627B4E), CircleShape)
                )
            }

            Text(
                text = "Simplificando a gestão de\nentregas em condomínios",
                color = Color(0xFF294B29),
                fontFamily = poppinsFamily,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 30.sp,
                letterSpacing = 0.21.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = "O Zé Porteiro visa otimizar a gestão de entregas em condomínios, proporcionando um sistema simples que reduz o tempo e erros, aumentando a satisfação dos moradores e a eficiência da portaria.",
                color = Color(0xFF71727A),
                fontSize = 18.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.11.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {  navController.navigate("login")},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF294B29))
            ) {
                Text(
                    text = "Começar agora",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun WelcomeScreenPreview() {
//    ZePorteiroAppTheme {
//        WelcomeScreen()
//    }
//}