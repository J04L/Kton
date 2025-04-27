package com.example.kton.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.kton.domain.model.RecetaDomain
import com.example.kton.presentation.models.GraficPie

@Composable
fun RecetaScreen (receta: RecetaDomain){
    val tabs = listOf("Overview", "Ingredients", "Directions")
    // Estado para rastrear la pestaña seleccionada
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(){
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.LightGray
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title, fontSize = 16.sp) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    selectedContentColor = Color.Black, // Color del texto cuando está seleccionado
                    unselectedContentColor = Color.Gray // Color del texto cuando no está seleccionado
                )
            }
        }
        // Contenido de cada pestaña
        when (selectedTabIndex) {
            0 -> OverviewContent(receta)
            1 -> IngredientsContent(receta)
            2 -> DirectionsContent(receta)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IngredientsContent(receta : RecetaDomain){

    Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
        //ingredientes
        Column(modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Black)
            .padding(30.dp, 20.dp)) {
            for (ingrediente in receta.ingredientes) {

                Row (modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF242424))
                    .padding(20.dp, 4.dp)
                ){
                    Text(text = ingrediente.nombre)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = ingrediente.cantidad)
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        Spacer(Modifier.height(10.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)){
            Text(text = "Información Nutricional")
            Row(modifier = Modifier.fillMaxSize()) {
                val proteinas = receta.informacionNutricional.proteinas
                val carbohidratos = receta.informacionNutricional.carbohidratos
                val grasas = receta.informacionNutricional.grasas
                val pies = listOf<GraficPie>(
                    GraficPie(proteinas.toFloat(), Color(0xFFE57373), "Proteinas"),
                    GraficPie(carbohidratos.toFloat(), Color(0xFFFF8a65), "Carbohidrátos"),
                    GraficPie(grasas.toFloat(), Color(0xFFFFD166), "Grasas")
                )
                for(pie in pies){
                    Box(modifier = Modifier.weight(0.33f)){
                        pie.PieChartWithLabels(radius = 250f)
                    }
                }
            }
        }
    }
}

@Composable
fun DirectionsContent(receta: RecetaDomain){


}

@Composable
fun OverviewContent(receta: RecetaDomain){
    Column(modifier = Modifier.fillMaxSize()) {
        //header
        Image(
            painter = rememberAsyncImagePainter("TODO"),
            contentDescription = "imgReceta",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
                .clip(RoundedCornerShape(15.dp))
        )
        Text(
            text = receta.titulo,
            fontWeight = FontWeight.Bold
        )
        Text(text = receta.descripcion, fontSize = 9.sp)
    }
}