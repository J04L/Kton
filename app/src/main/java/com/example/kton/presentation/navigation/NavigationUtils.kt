package com.example.kton.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kton.presentation.ui.SearchRecetasScreen
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kton.domain.states.SessionState
import com.example.kton.domain.viewModels.LoginViewModel
import com.example.kton.domain.viewModels.RecetaViewModel
import com.example.kton.domain.viewModels.RegisterViewModel
import com.example.kton.domain.viewModels.SessionViewModel
import com.example.kton.presentation.ui.LoadScreen
import com.example.kton.presentation.ui.LoginScreen
import com.example.kton.presentation.ui.RecetaScreen
import com.example.kton.presentation.ui.ScreenUsuario
import com.example.kton.utils.ProfileScreen
import com.example.registrousuario.RegisterScreen


sealed class BarScreen(val route: String, val title: String, val icon: ImageVector) {
    object Profile : BarScreen("profile", "Perfil", Icons.Filled.Home)
    object SearchRecetas : BarScreen("search", "Buscar", Icons.Filled.Search)
    object Objective : BarScreen("objective", "Objetivo", Icons.Filled.Person)
    object Create: BarScreen("create", "Crear", Icons.Filled.Add)

    companion object{
        val items = listOf<BarScreen>(Profile, SearchRecetas, Objective, Create)
    }
}
sealed class SimpleScreen(val route: String){
    object Receta : SimpleScreen("receta")
    object Login : SimpleScreen("login")
    object Register : SimpleScreen("register")
    object Splash : SimpleScreen("splash")
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    val recetaViewModel : RecetaViewModel = hiltViewModel()
    val loginViewModel : LoginViewModel = hiltViewModel()
    val registerViewModel : RegisterViewModel = hiltViewModel()
    val sessionViewModel : SessionViewModel = hiltViewModel()

    NavHost(navController, startDestination = SimpleScreen.Splash.route) {

        composable(SimpleScreen.Splash.route){
            LoadScreen()
            val sessionState by sessionViewModel.sessionState.collectAsState()

                // Navega según el estado de la sesión
                LaunchedEffect(sessionState) {
                    when (sessionState) {
                        SessionState.Authenticated -> {
                            navController.navigate(BarScreen.SearchRecetas.route) {
                                // Limpia la pila de navegación para evitar volver al splash
                                popUpTo(SimpleScreen.Splash.route) { inclusive = true }
                            }
                        }
                        SessionState.NotAuthenticated -> {
                            navController.navigate(SimpleScreen.Login.route) {
                                popUpTo(SimpleScreen.Splash.route) { inclusive = true }
                            }
                        }
                        SessionState.Loading -> {
                            // No hagas nada, sigue mostrando el splash
                        }
                    }
                }
        }

        composable(SimpleScreen.Login.route) {
            val success by loginViewModel.succes.collectAsState()
            val errorMessage by loginViewModel.error.collectAsState()

            LaunchedEffect(success) {
                if(success){
                    navController.navigate(BarScreen.SearchRecetas.route){
                        popUpTo(SimpleScreen.Login.route){ inclusive = true }
                    }
                }
            }
            LoginScreen(
                onLoginClick =  loginViewModel::login,
                onNavigateToRegister = {
                    navController.navigate(SimpleScreen.Register.route)
                },
                errorMessage = errorMessage,
                onDismissError = loginViewModel::clearError
            )
        }

        composable(SimpleScreen.Register.route){
            RegisterScreen(
                onRegisterClick = registerViewModel::register,
                onNavigateToLogin = {
                    navController.navigate(SimpleScreen.Login.route)
                }
            )
        }
        composable(BarScreen.Profile.route) { ScreenUsuario() }
        composable(BarScreen.SearchRecetas.route) {

            val navEvent by recetaViewModel.navigationEvents.collectAsState(null)
            LaunchedEffect(navEvent) {
                if(navEvent != null) navController.navigate(navEvent!!)
            }

            val recetasPaging = recetaViewModel.recetasPagingFlow.collectAsLazyPagingItems()
            SearchRecetasScreen(recetasPaging, recetaViewModel::onRecetaClick)
        }
        composable(BarScreen.Objective.route) { ProfileScreen() }
        composable(SimpleScreen.Receta.route) {
            val receta by recetaViewModel.recetaChosen.collectAsState()
            if (receta != null) RecetaScreen(receta!!)
        }
    }
}
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = BarScreen.items
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