package com.example.kton.presentation.models

import com.example.kton.domain.model.RecetaDomain

class RecetaUI(
    val receta: RecetaDomain,
    val size: Int
) {
    companion object{
        val SMALLSIZE : Int = 1
        val LARGESIZE : Int = 2
    }
}