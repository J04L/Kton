package com.example.kton.presentation.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.kton.domain.model.RecetaDomain
import com.example.kton.presentation.RecetaViewModel
import com.example.kton.presentation.models.GraficPie
import com.example.kton.presentation.models.RecetaUI


val roundCornerSize = 10.dp
val paddinItemReceta = 10.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuRecetasScreen(viewModel: RecetaViewModel = hiltViewModel(), navController: NavController) {
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    var recetas = viewModel.recetasPagingFlow.collectAsLazyPagingItems()

    //cuando se cambia el valor de navigationEvent
    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { route ->
            navController.navigate(route)
            Log.d("has clicado", "yeahh")
        }
    }

    //tengo que mostrarla y cada receta tiene un tamaño diferente aleatorio
    Column {
        SearchBar(
            query = query,
            onQueryChange = { query = it },  // Actualiza el estado con la nueva consulta
            onSearch = { isActive = false }, // Acción al hacer la búsqueda
            active = isActive,
            onActiveChange = { isActive = it }, // Cambia el estado de la barra
            placeholder = { Text("Buscar...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Borrar búsqueda")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

        }
        recetasBuscadasItem(recetas, viewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun recetasBuscadasItem(recetas: LazyPagingItems<RecetaUI>, viewModel: RecetaViewModel) {
    val cellSpace = 8.dp
    val smallSizeWeight = 0.35f
    val largeSizeWeight = 0.5f

    //contenedor de los items
    BoxWithConstraints {

        //itera las recetas y las muestra
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(cellSpace),
            verticalItemSpacing = cellSpace,
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            //iterador
            items(recetas.itemCount){ count ->
                //muestra la receta
                recetas[count]?.let { recetaUI ->
                    //si su tamaño es pequeño muestra el box 1x1
                    if (recetaUI.size == RecetaUI.SMALLSIZE)
                        recetaSamllItem(recetaUI.receta, this@BoxWithConstraints.maxHeight * smallSizeWeight, viewModel)
                    //si es largo muestra el box 1x2
                    else
                        recetaLargeItem(recetaUI.receta, (this@BoxWithConstraints.maxHeight * largeSizeWeight) + cellSpace, viewModel)

                }

            }
        }
    }
}
@Composable
private fun recetaSamllItem(receta: RecetaDomain, height: Dp, viewModel: RecetaViewModel = hiltViewModel()){
    Column(modifier = Modifier
        .height(height)
        .clip(RoundedCornerShape(roundCornerSize))
        .padding(paddinItemReceta)
        .clickable(onClick = {
            viewModel.recetaOnClick(receta)
        }),
        verticalArrangement = Arrangement.SpaceBetween){
        Text(text = receta.titulo)
        Text(text = receta.tiempo.toString())
    }
}
@Composable
private fun recetaLargeItem(receta: RecetaDomain, height: Dp, viewModel: RecetaViewModel = hiltViewModel()){
    val innerFontSize = 12.sp

    Column(modifier = Modifier
        .height(height)
        .clip(RoundedCornerShape(roundCornerSize))
        .padding(paddinItemReceta)
        .clickable(onClick = {
            viewModel.recetaOnClick(receta)
        }),
        verticalArrangement = Arrangement.SpaceBetween){
        Text(text = receta.titulo)
        Column (modifier = Modifier
            .clip(RoundedCornerShape(roundCornerSize))
            .background(Color.White)
            .padding(4.dp)) {
            Text(text = "${receta.tiempo} min",
                fontSize = innerFontSize)

            Text(text = receta.etiquetas.let { listaEtiquetas ->
                var str = ""
                for(a in 0..2){
                    str += "#${listaEtiquetas[a]} "
                }
                str
            },
                modifier = Modifier
                    .clip(RoundedCornerShape(roundCornerSize))
                    .background(Color.Black)
                    .padding(4.dp),
                fontSize = innerFontSize)
        }
    }
}

@Composable
fun RecetaScreen (viewModel: RecetaViewModel = hiltViewModel()){
    val receta = viewModel.recetaMostrada
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
