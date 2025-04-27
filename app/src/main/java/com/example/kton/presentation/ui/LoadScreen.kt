package com.example.kton.presentation.ui
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator // Usando Material 3
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

@Composable
fun LoadScreen(
    modifier: Modifier = Modifier,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth // Grosor por defecto de Material 3
) {
    // Box es un layout que permite apilar elementos o posicionarlos
    // de forma relativa a sus bordes. Es ideal para centrar un elemento.
    Box(
        // El modificador fillMaxSize hace que el Box ocupe todo el espacio
        // disponible en su contenedor padre (normalmente, toda la pantalla).
        modifier = modifier.fillMaxSize(),
        // contentAlignment = Alignment.Center centra el contenido del Box
        // tanto horizontal como verticalmente.
        contentAlignment = Alignment.Center
    ) {
        // CircularProgressIndicator es el Composable estándar de Material Design
        // para mostrar una rueda de carga indeterminada.
        CircularProgressIndicator(
            color = indicatorColor,
            strokeWidth = strokeWidth
        )
    }
}