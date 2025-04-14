package com.example.kton.presentation.ui

import android.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.kton.presentation.RecetaViewModel
import com.example.kton.presentation.UsuarioViewModel
import com.example.kton.utils.NivelesActividad
import com.example.kton.utils.historialRecetas
import com.example.kton.utils.nivelesDeActividad
import com.example.kton.utils.recetasFavoritas
import com.example.kton.utils.recetasGuardadas
import com.example.kton.utils.restriccionesAlimentarias
import java.nio.file.WatchEvent
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScreenUsuario(recetaViewModel: RecetaViewModel = hiltViewModel(),
                  usuarioViewModel: UsuarioViewModel = hiltViewModel()) {

      Column {
        //cuadro husuario
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(0.3f)
            .background(Color.Red)
        ) {
            Box(modifier = Modifier
                .weight(0.33f)
                .fillMaxSize()){
                Image(
                    painter = rememberAsyncImagePainter("https://imgs.search.brave.com/eD3hrSHZP_0wEL4_y9zN1eLjD1SiqXl3A6krBgF7vUQ/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly90NC5m/dGNkbi5uZXQvanBn/LzA1LzI3LzQ5Lzgz/LzM2MF9GXzUyNzQ5/ODM1MF82c2JtZ2RJ/bUdTWjBOOFNyMGND/VTQ4WjNtdmdCWXNC/ci5qcGc"),
                    contentDescription = "Descripción de la imagen",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .weight(0.33f)) {
                val actividad = NivelesActividad.Activo
                Spacer(modifier = Modifier.weight(0.4f))
                Box (modifier = Modifier.weight(0.25f)){
                    //Nivel actividad
                    NivelActividadFisicaItem(actividad)
                }
                Box(modifier = Modifier
                    .weight(0.25f)
                    .fillMaxHeight()){
                    Text(text = "Superavit", Modifier.align(Alignment.Center))
                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .weight(0.33f),
                horizontalAlignment = Alignment.End) {
                    Text(text = "Joel")
                    Text(text = "21")
                    Text(text = "Hombre")

            }
        }
        //hashtahags
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(0.3f)
            .background(Color.Black)){
            FlowRow(
                maxItemsInEachRow = 3
            ){
                for(i in restriccionesAlimentarias){
                    Text(text = "#$i", modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Cyan))
                }
            }
        }
        //historial
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(0.4f)
            .background(Color.Green)){
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "HISTOTIAL")
            }
        }
        //favoritos
        FlowRow (maxItemsInEachRow = 2,modifier = Modifier
            .background(Color.Yellow)
            .weight(0.2f)
            .fillMaxWidth()) {
            for(i in recetasFavoritas){
                Text(text = i, modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Green))
            }
        }
        LazyColumn(modifier = Modifier.weight(0.3f)) {
            items(recetasGuardadas.count()){item ->
               Row{
                   Image(
                       painter = rememberAsyncImagePainter(recetasGuardadas[item].url),
                       contentDescription = "receta img",
                       contentScale = ContentScale.Fit,
                       modifier = Modifier.height(100.dp).weight(0.5f)
                   )
                   Column {
                       Text(text = recetasGuardadas[item].nombre)
                       Text(text = "${recetasGuardadas[item].tiempo} min")
                   }
               }
            }
        }
    }
}

@Composable
fun NivelActividadFisicaItem(actividad: NivelesActividad) {
    Column {
        Text(text = actividad.name)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width // Usar size.width del Canvas
            val height = size.height
            val trapezoidWidth = width / 5f
            val trapezoidHeight = height

            for (i in 0 until nivelesDeActividad.count()) {
                drawTrapezoid(
                    topLeft = Offset((i * (trapezoidWidth -20f /*separacion entre trapecios*/)), 0f),
                    width = trapezoidWidth,
                    height = trapezoidHeight,
                    tilt = 70f, // Ángulo de inclinación en grados
                    full = i < actividad.level
                )
            }
        }
    }
}
fun DrawScope.drawTrapezoid(topLeft: Offset, width: Float, height: Float, tilt: Float, full: Boolean = false) {
    val path = Path().apply {

        // Definir los vértices del trapecio
        moveTo(topLeft.x + (width/3), 0f) // Top-left
        lineTo(topLeft.x + width, 0f) // Top-right
        lineTo(topLeft.x + width - (width/3), height) // Bottom-right
        lineTo(topLeft.x, height) // Bottom-left
        close() // Cerrar el path
    }

    // Dibujar el trapecio
    drawPath(
        path = path,
        color = Color.Yellow,
        style = if (full) Fill else Stroke(width = 5f)
    )
}