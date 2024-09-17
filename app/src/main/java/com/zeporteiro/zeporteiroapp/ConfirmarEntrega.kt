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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme

class ConfirmarEntrega : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZePorteiroAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ConfirmarEntregaScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ConfirmarEntregaScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Header4()
        Encomenda()
        ConfirmarEntregaView()
        ConfirmarRetiradaButton()
        Spacer(modifier = Modifier.height(95.dp))
        BottomNavigationBar2()
    }
}

@Composable
fun Header4() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Confirmar\nEntrega",
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
fun Encomenda() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Encomenda #221",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D1B34),
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = "Porteiro Leandro",
                fontSize = 14.sp,
                color = Color(0xFF8696BB),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.icon_calendar),
                    contentDescription = "Calendário",
                    modifier = Modifier
                        .padding(end = 9.dp,)
                        .width(16.dp)
                        .height(16.dp)
                )
                Text(
                    text = "06/08/2024",
                    fontSize = 12.sp,
                    color = Color(0xFF6B6E82),
                    modifier = Modifier.padding(end = 16.dp)
                )
                Image(
                    painter = painterResource(id = R.mipmap.icon_clock),
                    contentDescription = "Horário",
                    modifier = Modifier
                        .padding(end = 9.dp,)
                        .width(16.dp)
                        .height(16.dp)
                )
                Text(
                    text = "14:42:08",
                    fontSize = 12.sp,
                    color = Color(0xFF6B6E82)
                )
            }
        }
        }
    }


@Composable
fun ConfirmarEntregaView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, bottom = 25.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Text(
            "Cancelar",
            color = Color(0xFF627B4D),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(bottom = 8.dp, start = 15.dp, top = 10.dp)
        )
        Text(
            text = "Confirmar dados da retirada",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF294B29),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Encomenda #221",
            color = Color(0xFF1F2024),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 5.dp, start = 17.dp)
        )

        Text(
            text = "Destinatário Usuário",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF71727A),
            modifier = Modifier.padding(bottom = 16.dp, start = 17.dp)
        )
        Row {
            Image(
                painter = painterResource(id = R.mipmap.icon_calender_black),
                contentDescription = "Calendário",
                modifier = Modifier
                    .padding(end = 9.dp, start = 17.dp)
                    .width(16.dp)
                    .height(16.dp)
            )
            Text(
                text = "07/08/2024",
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier
            )
        }
        Row {
            Image(
                painter = painterResource(id = R.mipmap.icon_clock_black),
                contentDescription = "Horário",
                modifier = Modifier
                    .padding(end = 9.dp, start = 17.dp)
                    .width(16.dp)
                    .height(16.dp)
            )
            Text(
                text = "Retirado às 14:45:42",
                fontSize = 12.sp,
                color = Color.Black
            )
        }
        Text(
            text = "RECEBIDO POR",
            fontSize = 12.sp,
            color = Color(0xFF8F9098),
            modifier = Modifier.padding(top = 16.dp, start = 17.dp)
        )
        Row (
            Modifier.padding(bottom = 10.dp)
        ){
            Image(
                painter = painterResource(id = R.mipmap.icon_user),
                contentDescription = "Usuário",
                modifier = Modifier
                    .padding(end = 9.dp, top = 1.dp, start = 17.dp)
                    .width(20.dp)
                    .height(20.dp)
            )

            Text(
                text = "José Augusto",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
            )
        }
    }
}


@Composable
fun ConfirmarRetiradaButton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 45.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(color = Color(0xFF294B29))
            .shadow(
                elevation = 20.dp,
                spotColor = Color(0x1C5975A6),
            )
            .padding(vertical = 1.dp)
    ) {
        Text(
            text = "Confirmar Retirada",
            fontSize = 17.sp,
            color = Color.White,
            modifier = Modifier.padding(vertical = 12.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BottomNavigationBar2() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 70.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationItem(iconId = R.mipmap.icon_inicial_screen, label = "Página Inicial")
        NavigationItem(iconId = R.mipmap.icon_package, label = "Encomendas")
        NavigationItem(iconId = R.mipmap.icon_profile, label = "Perfil")
    }
}

@Composable
fun NavigationItem(iconId: Int, label: String) {
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
fun ConfirmarEntregaPreview() {
    ZePorteiroAppTheme {
        ConfirmarEntregaScreen()
    }
}
