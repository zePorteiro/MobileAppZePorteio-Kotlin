package com.zeporteiro.zeporteiroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeporteiro.zeporteiroapp.R
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme

class LoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZePorteiroAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
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
        Font(googleFont = poppins, fontProvider = fontProvider, weight = FontWeight.Black)
    )

    val interFamily = FontFamily(
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Light),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Normal),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Medium),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.SemiBold),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Bold),
        Font(googleFont = inter, fontProvider = fontProvider, weight = FontWeight.Black)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(457.dp)
                .background(Color(0xFFEAF2FF))
        ) {
            Image(
                painter = painterResource(id = R.mipmap.login_page_img),
                contentDescription = "Imagem decorativa de uma pessoa com encomendas",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Bem-vindo de volta!",
                fontFamily = poppinsFamily,
                fontSize = 35.sp,
                fontWeight = FontWeight.Black,
                color = Color(41, 75, 41, 255)
            )

            Spacer(modifier = Modifier.height(24.dp))

            LoginForm()

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Não possui conta?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                    )
                Text(text = " Cadastre-se",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(98, 123, 78, 255)
                )
            }
        }
    }
}

@Composable
fun LoginForm() {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Digite o seu email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Digite a sua senha") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = painterResource(id = if (isPasswordVisible) R.mipmap.hide_password_icon else R.mipmap.show_password_icon),
                        contentDescription = if (isPasswordVisible) "Esconder senha" else "Mostrar senha",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Esqueceu a sua senha?",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF627B4E)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF294B29))
        ) {
            Text(
                text = "Fazer Login",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ZePorteiroAppTheme {
        LoginScreen()
    }
}