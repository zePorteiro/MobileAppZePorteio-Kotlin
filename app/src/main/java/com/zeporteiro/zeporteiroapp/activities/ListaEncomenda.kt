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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeporteiro.zeporteiroapp.R
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme

class ListaEncomenda : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZePorteiroAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListaEncomendasScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ListaEncomendasScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Header3()
        Search()
        ConfirmarEncomenda()
        BottomNavigationBar()
    }
}

@Composable
fun Header3() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp)
            .padding(vertical = 16.dp),
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
                .background(Color.LightGray, shape = CircleShape)
                .size(30.dp)
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.icon_settings),
                contentDescription = "Configurações",
                modifier = Modifier
                    .width(20.dp)
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun Search() {
    Box(
        modifier = Modifier
            .padding(bottom = 13.dp, start = 16.dp, end = 16.dp)
            .clip(shape = RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = Color(0xFFEFEFEF),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.mipmap.icon_search),
                contentDescription = "Pesquisa",
                modifier = Modifier
                    .padding(end = 10.dp, top = 2.dp)
                    .width(20.dp)
                    .height(20.dp)
            )
            Text(
                "Pesquisar",
                color = Color(0xFF1F2024),
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
                    .padding(vertical = 8.dp)
            )
        }
    }


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 20.dp, start = 18.dp, end = 18.dp,)
            .fillMaxWidth()
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFC5C6CC),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(shape = RoundedCornerShape(12.dp))
                .width(106.dp)
                .padding(vertical = 8.dp, horizontal = 12.dp,)
        ){
            Image(
                painter = painterResource(id = R.mipmap.icon_order),
                contentDescription = "Ordenar",
                modifier = Modifier
                    .width(12.dp)
                    .height(12.dp)
            )
            Text("Ordenar",
                color = Color(0xFF1F2024),
                fontSize = 12.sp,
            )
            Image(
                painter = painterResource(id = R.mipmap.icon_arrow_down),
                contentDescription = "Arrow Down",
                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFC5C6CC),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(shape = RoundedCornerShape(12.dp))
                .width(106.dp)
                .padding(vertical = 8.dp, horizontal = 12.dp,)
        ){
            Image(
                painter = painterResource(id = R.mipmap.icon_filter),
                contentDescription = "Filtro",
                modifier = Modifier
                    .width(12.dp)
                    .height(12.dp)
            )
            Text("Filtrar",
                color = Color(0xFF1F2024),
                fontSize = 12.sp,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(21.dp)
                    .background(
                        color = Color(0xFF627B4D),
                        shape = CircleShape
                    )
            ){
                Text("2",
                    color = Color(0xFFFFFFFF),
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
fun ConfirmarEncomenda() {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp, start = 14.dp, end = 14.dp,)
            .clip(shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(
                color = Color(0xFF294B29),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 23.dp, horizontal = 20.dp,)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 10.dp,)
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .width(142.dp)
            ){
                Text("Encomenda #232",
                    color = Color(0xFFFFFFFF),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(bottom = 6.dp,)
                )
                Text("Porteiro José",
                    color = Color(0xFFB4B7BF),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start = 1.dp,)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(shape = RoundedCornerShape(12.dp))
                    .width(139.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                    )
                    .padding(vertical = 9.dp,)
            ){
                Text("Confirmar Entrega",
                    color = Color(0xFF294B29),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(bottom = 16.dp,)
                .height(1.dp)
                .fillMaxWidth()
        ){
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.mipmap.icon_calender_white),
                contentDescription = "Calendário",
                modifier = Modifier
                    .padding(end = 9.dp,)
                    .width(16.dp)
                    .height(16.dp)
            )
            Text("07/08/2024",
                color = Color(0xFFFFFFFF),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(end = 21.dp,)
            )
            Image(
                painter = painterResource(id = R.mipmap.icon_clock_white),
                contentDescription = "Horário",
                modifier = Modifier
                    .padding(end = 9.dp,)
                    .width(16.dp)
                    .height(16.dp)
            )
            Text("11:45:54",
                color = Color(0xFFFFFFFF),
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(bottom = 15.dp, start = 14.dp, end = 14.dp,)
            .clip(shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 23.dp, horizontal = 16.dp,)
    ){
        Text("Encomenda #221",
            color = Color(0xFF0D1B34),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 6.dp,)
        )
        Text("Porteiro Leandro",
            color = Color(0xFF8696BB),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(bottom = 15.dp,)
        )
        Column(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        ){
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.mipmap.icon_calendar),
                contentDescription = "Calendário",
                modifier = Modifier
                    .padding(end = 9.dp,)
                    .width(16.dp)
                    .height(16.dp)
            )
            Text("06/08/2024",
                color = Color(0xFF6B6E82),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(end = 22.dp,)
            )
            Image(
                painter = painterResource(id = R.mipmap.icon_clock),
                contentDescription = "Horário",
                modifier = Modifier
                    .padding(end = 9.dp,)
                    .width(16.dp)
                    .height(16.dp)
            )
            Text("14:42:08",
                color = Color(0xFF6B6E82),
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
        Column(
            modifier = Modifier
                .padding(bottom = 15.dp, start = 14.dp, end = 14.dp,)
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 23.dp, horizontal = 16.dp,)
        ){
            Text("Encomenda #201",
                color = Color(0xFF0D1B34),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 6.dp,)
            )
            Text("Porteiro Leandro",
                color = Color(0xFF8696BB),
                fontSize = 14.sp,

            )
        Column(
            modifier = Modifier
                .padding(bottom = 15.dp,)
                .height(1.dp)
                .fillMaxWidth()
        ){
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.mipmap.icon_calendar),
                contentDescription = "Calendário",
                modifier = Modifier
                    .padding(end = 9.dp,)
                    .width(16.dp)
                    .height(16.dp)
            )
            Text("06/08/2024",
                color = Color(0xFF6B6E82),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(end = 22.dp,)
            )
            Image(
                painter = painterResource(id = R.mipmap.icon_clock),
                contentDescription = "Horário",
                modifier = Modifier
                    .padding(end = 9.dp,)
                    .width(16.dp)
                    .height(16.dp)
            )
            Text("14:42:08",
                color = Color(0xFF6B6E82),
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationItem2(iconId = R.mipmap.icon_inicial_screen, label = "Página Inicial")
        NavigationItem2(iconId = R.mipmap.icon_package, label = "Encomendas")
        NavigationItem2(iconId = R.mipmap.icon_profile, label = "Perfil")
    }
}

@Composable
fun NavigationItem2(iconId: Int, label: String) {
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
fun ListaEncomendaPreview() {
    ZePorteiroAppTheme {
        ListaEncomendasScreen()
    }
}

