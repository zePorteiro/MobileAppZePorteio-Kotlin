package com.zeporteiro.zeporteiroapp.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zeporteiro.zeporteiroapp.utils.NavigationRoutes

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: String?
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Página Inicial",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Início", fontSize = 12.sp) },
            selected = currentRoute == NavigationRoutes.HOME,
            onClick = {
                navController.navigate(NavigationRoutes.HOME) {
                    popUpTo(NavigationRoutes.HOME) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF294B29),
                selectedTextColor = Color(0xFF294B29),
                indicatorColor = Color(0xFFE8F1E8)  // Verde mais claro para o fundo
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Encomendas",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Encomendas", fontSize = 12.sp) },
            selected = currentRoute == NavigationRoutes.LISTA_ENCOMENDAS,
            onClick = {
                navController.navigate(NavigationRoutes.LISTA_ENCOMENDAS) {
                    popUpTo(NavigationRoutes.HOME) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF294B29),
                selectedTextColor = Color(0xFF294B29),
                indicatorColor = Color(0xFFE8F1E8)  // Verde mais claro para o fundo
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Perfil", fontSize = 12.sp) },
            selected = currentRoute == NavigationRoutes.PROFILE,
            onClick = {
                navController.navigate(NavigationRoutes.PROFILE) {
                    popUpTo(NavigationRoutes.HOME) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF294B29),
                selectedTextColor = Color(0xFF294B29),
                indicatorColor = Color(0xFFE8F1E8)  // Verde mais claro para o fundo
            )
        )
    }
}