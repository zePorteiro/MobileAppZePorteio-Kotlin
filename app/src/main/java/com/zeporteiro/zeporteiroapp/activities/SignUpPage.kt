package com.zeporteiro.zeporteiroapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zeporteiro.zeporteiroapp.R
import com.zeporteiro.zeporteiroapp.ui.theme.ZePorteiroAppTheme
import com.zeporteiro.zeporteiroapp.viewModel.SignUpViewModel
import org.koin.java.KoinJavaComponent.inject

class SignUpPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZePorteiroAppTheme {
                val navController = rememberNavController()
                SignUpScreen(navController = navController)
            }
        }
    }
}

@Composable
fun SignUpScreen(navController: NavController) {
    val viewModel: SignUpViewModel by inject(SignUpViewModel::class.java)
    val scrollState = rememberScrollState()

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    var senhaVisivel by remember { mutableStateOf(false) }
    var confirmarSenhaVisivel by remember { mutableStateOf(false) }

    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(24.dp)
                .systemBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Faça o seu cadastro",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF294B29)
            )

            Text(
                text = "Crie uma conta para começar a controlar suas encomendas",
                fontSize = 15.sp,
                color = Color(0xFF71727A),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CampoInput(
                value = nome,
                onValueChange = { nome = it },
                label = "Nome completo",
                placeholder = "Digite o seu nome"
            )

            CampoInput(
                value = email,
                onValueChange = { email = it },
                label = "E-mail",
                placeholder = "Digite o seu e-mail",
                keyboardType = KeyboardType.Email
            )

            CampoTelefone(
                value = telefone,
                onValueChange = { telefone = it },
                label = "Telefone Celular",
                placeholder = "DDD + Telefone celular"
            )

            InputSenha(
                value = senha,
                onValueChange = { senha = it },
                label = "Senha",
                placeholder = "Digite a sua senha",
                passwordVisible = senhaVisivel,
                onVisibilityChange = { senhaVisivel = it }
            )

            Text(
                text = "A senha precisa de:\nMínimo 6 caracteres;\nNúmeros e caracteres especiais;",
                fontSize = 10.sp,
                color = Color(0xFF8F9098),
                modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
            )

            InputSenha(
                value = confirmarSenha,
                onValueChange = { confirmarSenha = it },
                label = "Confirmar Senha",
                placeholder = "Digite sua senha novamente",
                passwordVisible = confirmarSenhaVisivel,
                onVisibilityChange = { confirmarSenhaVisivel = it }
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
                    if (viewModel.setDadosPessoais(nome, email, telefone, senha, confirmarSenha)) {
                        navController.navigate("signup2")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF294B29)),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        "Avançar",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Já possui conta? ",
                    color = Color(0xFF71727A),
                    fontSize = 12.sp
                )
                Text(
                    "Acesse agora",
                    color = Color(0xFF3D3D3D),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CampoInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF3D3D3D)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}

@Composable
fun CampoTelefone(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF3D3D3D)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                val digitsOnly = newValue.filter { it.isDigit() }
                val masked = when {
                    digitsOnly.length <= 2 -> digitsOnly
                    digitsOnly.length <= 7 -> "(${digitsOnly.substring(0,2)}) ${digitsOnly.substring(2)}"
                    digitsOnly.length <= 11 -> "(${digitsOnly.substring(0,2)}) ${digitsOnly.substring(2,7)}-${digitsOnly.substring(7)}"
                    else -> "(${digitsOnly.substring(0,2)}) ${digitsOnly.substring(2,7)}-${digitsOnly.substring(7,11)}"
                }
                if (masked.length <= 16) onValueChange(masked)
            },
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
    }
}

@Composable
fun InputSenha(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    passwordVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF3D3D3D)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { onVisibilityChange(!passwordVisible) }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) {
                                R.drawable.ic_visibility_on
                            } else {
                                R.drawable.ic_visibility_off
                            }
                        ),
                        contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha"
                    )
                }
            }
        )
    }
}

//@Composable
//fun SignUpScreen(
//    navController: NavController
//) {
//
//    val viewModel: SignUpViewModel by inject(SignUpViewModel::class.java)
//
//    var nome by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var telefone by remember { mutableStateOf("") }
//    var senha by remember { mutableStateOf("") }
//    var confirmarSenha by remember { mutableStateOf("") }
//
//    val error by viewModel.error.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Top
//    ) {
//        Text(
//            text = "Faça o seu cadastro",
//            fontSize = 28.sp,
//            fontWeight = FontWeight.ExtraBold,
//            color = Color(0xFF294B29)
//        )
//        Text(
//            text = "Crie uma conta para começar a controlar suas encomendas",
//            fontSize = 15.sp,
//            color = Color(0xFF71727A),
//            modifier = Modifier.padding(top = 8.dp)
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        CampoInput(
//            value = nome,
//            onValueChange = { nome = it },
//            label = "Nome completo",
//            placeholder = "Digite o seu nome"
//        )
//
//        CampoInput(
//            value = email,
//            onValueChange = { email = it },
//            label = "E-mail",
//            placeholder = "Digite o seu e-mail",
//            keyboardType = KeyboardType.Email
//        )
//
//        CampoInput(
//            value = telefone,
//            onValueChange = { telefone = it },
//            label = "Telefone Celular",
//            placeholder = "DDD + Telefone celular",
//            keyboardType = KeyboardType.Phone
//        )
//
//        InputSenha(
//            value = senha,
//            onValueChange = { senha = it },
//            label = "Senha",
//            placeholder = "Digite a sua senha"
//        )
//
//        Text(
//            text = "A senha precisa de:\nMínimo 6 caracteres;\nNúmeros e caracteres especiais;",
//            fontSize = 10.sp,
//            color = Color(0xFF8F9098),
//            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
//        )
//
//        InputSenha(
//            value = confirmarSenha,
//            onValueChange = { confirmarSenha = it },
//            label = "Confirmar Senha",
//            placeholder = "Digite sua senha novamente"
//        )
//
//        error?.let {
//            Text(
//                text = it,
//                color = Color.Red,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(
//            onClick = {
//                if (viewModel.setDadosPessoais(nome, email, telefone, senha, confirmarSenha)) {
//                    navController.navigate("signup2")
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(48.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF294B29)),
//            enabled = !isLoading
//        ) {
//            if (isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier.size(24.dp),
//                    color = Color.White
//                )
//            } else {
//                Text(
//                    "Avançar",
//                    color = Color.White,
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.SemiBold
//                )
//            }
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(
//                "Já possui conta? ",
//                color = Color(0xFF71727A),
//                fontSize = 12.sp
//            )
//            Text(
//                "Acesse agora",
//                color = Color(0xFF3D3D3D),
//                fontSize = 12.sp,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.clickable {
//                    navController.popBackStack()
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun CampoInput(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    placeholder: String,
//    keyboardType: KeyboardType = KeyboardType.Text
//) {
//    Column(modifier = Modifier.padding(vertical = 6.dp)) {
//        Text(
//            text = label,
//            fontSize = 12.sp,
//            fontWeight = FontWeight.SemiBold,
//            color = Color(0xFF3D3D3D)
//        )
//
//        OutlinedTextField(
//            value = value,
//            onValueChange = onValueChange,
//            placeholder = { Text(placeholder) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(48.dp),
//            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
//        )
//    }
//}
//
//@Composable
//fun InputSenha(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    placeholder: String
//) {
//    Column(modifier = Modifier.padding(vertical = 6.dp)) {
//        Text(
//            text = label,
//            fontSize = 12.sp,
//            fontWeight = FontWeight.SemiBold,
//            color = Color(0xFF3D3D3D)
//        )
//
//        OutlinedTextField(
//            value = value,
//            onValueChange = onValueChange,
//            placeholder = { Text(placeholder) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(48.dp),
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview() {
//    ZePorteiroAppTheme {
//        val navController = rememberNavController()
//        SignUpScreen(navController = navController)
//    }
//}