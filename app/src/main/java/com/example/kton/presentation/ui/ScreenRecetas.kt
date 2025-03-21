package com.example.kton.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kton.presentation.RecetaViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuRecetasScreen(viewModel: RecetaViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    var map : MutableMap<String, Boolean> by remember {mutableStateOf(mutableMapOf())}
    var suggestions = listOf<String>()
    suggestions.forEach { item ->
        if(Random.nextInt(100) > 30 ) map.put(item, true)
        else map.put(item,false)
    }

    //tengo la lista
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
            // Sugerencias basadas en la consulta
            suggestions.filter { it.contains(query, ignoreCase = true) }.forEach { suggestion ->
                ListItem(
                    headlineContent = { Text(suggestion) },
                    leadingContent = { Icon(Icons.Default.Place, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            query = suggestion
                            isActive = false // Cierra la barra de búsqueda
                        }
                )
            }
        }
        recetasBuscadasItem(map)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun recetasBuscadasItem(recetas: MutableMap<String,Boolean>) {
    val cellSpace = 8.dp
    BoxWithConstraints {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(cellSpace),
            verticalItemSpacing = cellSpace,
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            items(recetas.keys.count()){ item ->
                if (recetas.getValue(recetas.keys.toList()[item]))
                    recetaItem(recetas.keys.toList()[item], this@BoxWithConstraints.maxHeight * 0.2f)
                else
                    recetaItem(recetas.keys.toList()[item], (this@BoxWithConstraints.maxHeight * 0.4f) + cellSpace)
            }
        }
    }
}
@Composable
fun recetaItem(receta:String, height: Dp){
    Box(modifier = Modifier
        .height(height)
        .border(width = 2.dp, color = Color.Blue)){
        Text(text = receta)
    }
}
