package com.zeporteiro.zeporteiroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme

class Profile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZePorteiroAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfileScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header5()
        Spacer(modifier = Modifier)
        ProfileField()
        BottomNavigationBar3()
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
            .padding(top = 30.dp,start = 13.dp,end = 13.dp,)
    ){
        Text("Nome Completo",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(bottom = 6.dp)
        )
        Column(
            modifier = Modifier
                .padding(bottom = 22.dp,)
                .clip(shape = RoundedCornerShape(9.dp))
                .fillMaxWidth()
                .background(
                    color = Color(0xFFEFEFEF),
                    shape = RoundedCornerShape(9.dp)
                )
                .padding(vertical = 19.dp,horizontal = 25.dp,)
        ){
            Text("Nome do morador",
                color = Color(0xFF000000),
                fontSize = 14.sp,
            )
        }
        Text("E-mail cadastrado",
            color = Color(0xFF1F2024),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 6.dp,)
        )
        Column(
            modifier = Modifier
                .padding(bottom = 21.dp,)
                .clip(shape = RoundedCornerShape(9.dp))
                .fillMaxWidth()
                .background(
                    color = Color(0xFFEFEFEF),
                    shape = RoundedCornerShape(9.dp)
                )
                .padding(vertical = 18.dp,horizontal = 24.dp,)
        ){
            Text("exemplo@email.com",
                color = Color(0xFF000000),
                fontSize = 14.sp,
            )
        }
        Text("Telefone celular",
            color = Color(0xFF1F2024),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 6.dp,)
        )
        Column(
            modifier = Modifier
                .padding(bottom = 21.dp,)
                .clip(shape = RoundedCornerShape(9.dp))
                .fillMaxWidth()
                .background(
                    color = Color(0xFFEFEFEF),
                    shape = RoundedCornerShape(9.dp)
                )
                .padding(vertical = 16.dp,horizontal = 24.dp,)
        ){
            Text("(11) 98746-1847",
                color = Color(0xFF000000),
                fontSize = 14.sp,
            )
        }
        Text("Telefone fixo",
            color = Color(0xFF1F2024),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 6.dp,)
        )
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp,)
                .clip(shape = RoundedCornerShape(9.dp))
                .fillMaxWidth()
                .background(
                    color = Color(0xFFEFEFEF),
                    shape = RoundedCornerShape(9.dp)
                )
                .padding(top = 23.dp,bottom = 13.dp,start = 23.dp,end = 23.dp,)
        ){
            Text("-",
                color = Color(0xFF000000),
                fontSize = 14.sp,
            )
        }
        Text("Número do apartamento",
            color = Color(0xFF1F2024),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 6.dp,)
        )
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(9.dp))
                .fillMaxWidth()
                .background(
                    color = Color(0xFFEFEFEF),
                    shape = RoundedCornerShape(9.dp)
                )
                .padding(vertical = 19.dp,horizontal = 23.dp,)
        ){
            Text("101",
                color = Color(0xFF000000),
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun BottomNavigationBar3() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 65.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationItem3(iconId = R.mipmap.icon_inicial_screen, label = "Página Inicial")
        NavigationItem3(iconId = R.mipmap.icon_package_gray, label = "Encomendas")
        NavigationItem3(iconId = R.mipmap.icon_profile_black, label = "Perfil")
    }
}

@Composable
fun NavigationItem3(iconId: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color(0xFF71727A)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ZePorteiroAppTheme {
        ProfileScreen()
    }
}
