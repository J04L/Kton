package com.example.kton.presentation.models

import com.example.kton.domain.model.RecetaDomain

class RecetaUI(
    val receta: RecetaDomain,
    val size: Int
) {
    companion object{
        val SMALLWEIGHT : Int = 1
        val LARGEWEIGHT : Int = 2
    }
}