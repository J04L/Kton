package com.example.kton.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kton.data.network.models.RecetaResponse
import com.example.kton.domain.model.Receta
import com.example.kton.domain.repository.toDomain
import com.example.kton.presentation.RecetaViewModel
import com.example.kton.presentation.navigation.BottomNavigationBar
import com.example.kton.presentation.navigation.NavigationGraph
import com.example.kton.presentation.theme.KtonTheme
import com.example.kton.utils.json
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KtonTheme {
                val recetaViewModel : RecetaViewModel = hiltViewModel()

                val recetas = remember { recetaViewModel.recetasPagingFlow }.collectAsLazyPagingItems()

                if (recetas.itemCount > 0) {
                    Log.d("hola", recetas[0]?.titulo.toString())
                } else {
                    Log.d("hola", "Aún no se han cargado recetas")
                }

            }
        }
    }
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            NavigationGraph(navController)
        }
    }
}