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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zeporteiro.zeporteiroapp.R
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme

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

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Header5()
        Spacer(modifier = Modifier)
        ProfileField()
    }
}

@Composable
fun Header5() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Meu Perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF294B29),
        )

    }
}

@Composable
fun ProfileField() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 13.dp, end = 13.dp)
    ) {
        ProfileItemField(
            label = "Nome Completo",
            value = "Nome do morador"
        )
        ProfileItemField(
            label = "E-mail cadastrado",
            value = "exemplo@email.com"
        )
        ProfileItemField(
            label = "Telefone celular",
            value = "(11) 98746-1847"
        )
        ProfileItemField(
            label = "Telefone fixo",
            value = "-"
        )
        ProfileItemField(
            label = "Número do apartamento",
            value = "101"
        )
    }
}

@Composable
fun ProfileItemField(
    label: String,
    value: String
) {
    Text(
        text = label,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 6.dp)
    )
    Column(
        modifier = Modifier
            .padding(bottom = 22.dp)
            .clip(shape = RoundedCornerShape(9.dp))
            .fillMaxWidth()
            .background(
                color = Color(0xFFEFEFEF),
                shape = RoundedCornerShape(9.dp)
            )
            .padding(vertical = 19.dp, horizontal = 25.dp)
    ) {
        Text(
            text = value,
            color = Color(0xFF000000),
            fontSize = 14.sp,
        )
    }
}

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
