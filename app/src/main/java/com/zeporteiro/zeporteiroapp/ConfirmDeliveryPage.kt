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
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme

class ConfirmDeliveryPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZePorteiroAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ConfirmDeliveryScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ConfirmDeliveryScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 77.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Header2()
            Spacer(modifier = Modifier.height(51.dp))
            CardEncomenda()
            Spacer(modifier = Modifier.height(51.dp))
            ConfirmButton()
        }
        Spacer(modifier = Modifier.weight(1f))
        Navegacao()
    }
}

@Composable
fun Header2() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Confirmar\nEntrega",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF294B29),
            lineHeight = 24.sp,
            letterSpacing = (-0.24).sp
        )
        Image(
            painter = painterResource(id = R.mipmap.icon_settings),
            contentDescription = "Configurações",
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun CardEncomenda() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
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
fun ConfirmButton() {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF294B29))
    ) {
        Text(
            text = "Confirmar",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun Navegacao() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavegacaoItem2(
            icon = R.mipmap.icon_inicial_screen,
            label = "Página Inicial"
        )
        NavegacaoItem2(
            icon = R.mipmap.icon_package,
            label = "Encomendas"
        )
        NavegacaoItem2(
            icon = R.mipmap.icon_profile,
            label = "Perfil"
        )
    }
}

@Composable
fun NavegacaoItem2(icon: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
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
fun ConfirmDeliveryPagePreview() {
    ZePorteiroAppTheme {
        ConfirmDeliveryScreen()
    }
}


