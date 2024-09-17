package com.zeporteiro.zeporteiroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SignUp2Page : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZePorteiroAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CadastroScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CadastroScreen(modifier: Modifier = Modifier) {
    var cep by remember { mutableStateOf("") }
    var logradouro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var bloco by remember { mutableStateOf("") }
    var nomeCondominio by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Faça o seu cadastro",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF294B29)
        )
        Text(
            text = "Crie uma conta para começar a controlar suas encomendas",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CadastroForm(
            cep = cep,
            onCepChange = { cep = it },
            logradouro = logradouro,
            onLogradouroChange = { logradouro = it },
            numero = numero,
            onNumeroChange = { numero = it },
            complemento = complemento,
            onComplementoChange = { complemento = it },
            bloco = bloco,
            onBlocoChange = { bloco = it },
            nomeCondominio = nomeCondominio,
            onNomeCondominioChange = { nomeCondominio = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF294B29))
        ) {
            Text("Finalizar Cadastro")
        }

        Text(
            text = "Voltar a página",
            fontSize = 12.sp,
            color = Color(0xFF71727A),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CadastroForm(
    cep: String,
    onCepChange: (String) -> Unit,
    logradouro: String,
    onLogradouroChange: (String) -> Unit,
    numero: String,
    onNumeroChange: (String) -> Unit,
    complemento: String,
    onComplementoChange: (String) -> Unit,
    bloco: String,
    onBlocoChange: (String) -> Unit,
    nomeCondominio: String,
    onNomeCondominioChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CadastroTextField(
            value = cep,
            onValueChange = onCepChange,
            label = "CEP*",
            placeholder = "Digite o seu cep",
            keyboardType = KeyboardType.Number
        )
        CadastroTextField(
            value = logradouro,
            onValueChange = onLogradouroChange,
            label = "Logradouro*",
            placeholder = "Digite o logradouro"
        )
        CadastroTextField(
            value = numero,
            onValueChange = onNumeroChange,
            label = "Número*",
            placeholder = "Digite o número",
            keyboardType = KeyboardType.Number
        )
        CadastroTextField(
            value = complemento,
            onValueChange = onComplementoChange,
            label = "Complemento*",
            placeholder = "Digite o número do apartamento"
        )
        CadastroTextField(
            value = bloco,
            onValueChange = onBlocoChange,
            label = "Bloco",
            placeholder = "Digite o bloco em que reside"
        )
        CadastroTextField(
            value = nomeCondominio,
            onValueChange = onNomeCondominioChange,
            label = "Nome do Condomínio*",
            placeholder = "Digite o nome do condomínio"
        )
    }
}

@Composable
fun CadastroTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ZePorteiroAppTheme {
        CadastroScreen()
    }
}