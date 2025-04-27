package com.example.kton.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.kton.domain.model.RecetaDomain
import com.example.kton.domain.viewModels.RecetaViewModel
import com.example.kton.presentation.models.GraficPie
import com.example.kton.presentation.models.RecetaUI
import com.example.kton.presentation.theme.paddings


val roundCornerSize = 10.dp
val paddinItemReceta = 10.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRecetasScreen(
    recetasPaging: LazyPagingItems<RecetaUI>,
    onRecetaClick: (RecetaDomain) -> Unit

) {
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }


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
        //itera las recetas y las muestra
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(MaterialTheme.paddings.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.paddings.small),
            verticalItemSpacing = MaterialTheme.paddings.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.small)
        ) {
            //iterador
            items(recetasPaging.itemCount){ i ->

                //muestra la receta
                recetasPaging[i]?.let { recetaUI ->
                    //si su tamaño es pequeño muestra el box 1x1
                    if (recetaUI.size == RecetaUI.SMALLWEIGHT)
                        Column(modifier = Modifier
                            .weight((recetaUI.size/7f).toFloat())
                            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                            .padding(paddinItemReceta)
                            .clickable(onClick = {
                                onRecetaClick(recetaUI.receta)
                            }),
                            verticalArrangement = Arrangement.SpaceBetween){
                            Text(text = recetaUI.receta.titulo)
                            Text(text = recetaUI.receta.tiempo.toString())
                        }
                    //si es largo muestra el box 1x2
                    else if (recetaUI.size == RecetaUI.LARGEWEIGHT)
                        Column(modifier = Modifier
                            .weight((recetaUI.size/7f))
                            .clip(RoundedCornerShape(roundCornerSize))
                            .padding(paddinItemReceta)
                            .clickable(onClick = {
                                onRecetaClick(recetaUI.receta)
                            }),
                            verticalArrangement = Arrangement.SpaceBetween){
                            Text(text = recetaUI.receta.titulo)
                            Column (modifier = Modifier
                                .clip(RoundedCornerShape(roundCornerSize))
                                .background(Color.White)
                                .padding(4.dp)) {
                                Text(text = "${recetaUI.receta.tiempo} min",
                                    style = MaterialTheme.typography.bodySmall)

                                Text(text = recetaUI.receta.etiquetas.let { listaEtiquetas ->
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
                                    style = MaterialTheme.typography.bodySmall)
                            }
                        }

                }

            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun recetasBuscadasItem(recetas: LazyPagingItems<RecetaUI>) {

    val smallSizeWeight = 0.35f
    val largeSizeWeight = 0.5f


}
@Composable
private fun recetaSamllItem(receta: RecetaDomain, height: Dp, viewModel: RecetaViewModel = hiltViewModel()){

}
@Composable
private fun recetaLargeItem(receta: RecetaDomain, height: Dp, viewModel: RecetaViewModel = hiltViewModel()){
    val innerFontSize = 12.sp

}

