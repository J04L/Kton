package com.example.kton.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kton.presentation.ui.MenuRecetasScreen
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kton.presentation.RecetaViewModel
import com.example.kton.presentation.ui.RecetaScreen
import com.example.kton.presentation.ui.ScreenUsuario
import com.example.kton.utils.ProfileScreen

sealed class BarScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : BarScreen("home", "Inicio", Icons.Filled.Home)
    object Search : BarScreen("search", "Buscar", Icons.Filled.Search)
    object Profile : BarScreen("profile", "Perfil", Icons.Filled.Person)
}
sealed class Screen(val route: String){
    object Receta : Screen("receta")
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    val recetaViewModel : RecetaViewModel = viewModel()
    NavHost(navController, startDestination = BarScreen.Home.route) {
        composable(BarScreen.Home.route) { ScreenUsuario() }
        composable(BarScreen.Search.route) { MenuRecetasScreen(viewModel=recetaViewModel, navController= navController) }
        composable(BarScreen.Profile.route) { ProfileScreen() }
        composable(Screen.Receta.route) { RecetaScreen(recetaViewModel) }
    }
}
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BarScreen.Home,
        BarScreen.Search,
        BarScreen.Profile
    )
    NavigationBar {
        //encuentra la pantalla en la que estámos actualemte
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        //se crea un elemento NavigationBarItem por cada iteración
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    //al cliclar nos manda a la ruta correspondiente
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}