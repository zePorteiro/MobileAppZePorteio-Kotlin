package com.zeporteiro.zeporteiroapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zeporteiro.zeporteiroapp.R
import com.zeporteiro.zeporteiroapp.viewModel.LoginPageViewModel
import com.zeporteiro.zeporteiroapp.viewModel.SignUpViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.inject

class SignUp2Page : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZePorteiroAppTheme {
                val navController = rememberNavController()
                CadastroScreen(
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun CadastroScreen(
//    viewModel: SignUpViewModel = koinViewModel(),
    navController: NavController
) {

    val viewModel: SignUpViewModel by inject(SignUpViewModel::class.java)

    var cep by remember { mutableStateOf("") }
    var logradouro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var bloco by remember { mutableStateOf("") }
    var nomeCondominio by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val cadastroSuccess by viewModel.cadastroSuccess.collectAsState()

    LaunchedEffect(cadastroSuccess) {
        if (cadastroSuccess) {
            navController.navigate("login") {
                popUpTo("welcome") { inclusive = true }
            }
        }
    }

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
            numeroApartamento = complemento,
            onNumeroApartamentoChange = { complemento = it },
            bloco = bloco,
            onBlocoChange = { bloco = it },
            nomeCondominio = nomeCondominio,
            onNomeCondominioChange = { nomeCondominio = it }
        )

        error?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.setDadosEndereco(
                    cep = cep,
                    logradouro = logradouro,
                    numero = numero,
                    numeroApartamento = complemento,
                    bloco = bloco,
                    nomeCondominio = nomeCondominio
                )
                viewModel.cadastrar()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF294B29))
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Finalizar Cadastro")
            }
        }

        Text(
            text = "Voltar",
            fontSize = 12.sp,
            color = Color(0xFF71727A),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .clickable { navController.popBackStack() }
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
    numeroApartamento: String,
    onNumeroApartamentoChange: (String) -> Unit,
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
            value = numeroApartamento,
            onValueChange = onNumeroApartamentoChange,
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

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview3() {
//    ZePorteiroAppTheme {
//        CadastroScreen()
//    }
//}