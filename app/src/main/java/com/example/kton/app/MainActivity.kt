package com.example.kton.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kton.presentation.navigation.BarScreen
import com.example.kton.presentation.navigation.BottomNavigationBar
import com.example.kton.presentation.navigation.NavigationGraph
import com.example.kton.presentation.theme.KtonTheme
import com.example.kton.presentation.ui.LoginScreen
import com.example.kton.utils.LoginScreenPreview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            KtonTheme {
                MainScreen()
            }
        }
    }
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route.toString()

    val bottomBarRoutes = BarScreen.items.map { it.route }


    Scaffold(modifier = Modifier
        .fillMaxSize(),
        bottomBar = { if(currentScreen in bottomBarRoutes) BottomNavigationBar(navController)}
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            NavigationGraph(navController)
        }
    }
}