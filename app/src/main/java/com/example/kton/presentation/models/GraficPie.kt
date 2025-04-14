package com.example.kton.presentation.models

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

class GraficPie(
    val porcentaje: Float,
    val color : Color,
    val label : String,
){
    @Composable
    fun PieChartWithLabels(
        modifier: Modifier = Modifier,
        radius: Float = 300f,
    ) {
        Canvas(modifier = modifier.fillMaxSize().background(Color.Black)) {
            val canvasWidth = size.width
            val canvasHeight = (radius/2)
            val center = Offset(canvasWidth / 2f, canvasHeight)
            val totalValue = 100

            var startAngle = 295f

            // Configuración del Paint para el texto
            val textPaint = Paint().asFrameworkPaint().apply {
                color = android.graphics.Color.WHITE // Color del texto
                textSize = 30f // Tamaño del texto en píxeles
                textAlign = android.graphics.Paint.Align.CENTER // Centrar el texto
                isFakeBoldText = true
            }

            val sweepAngle = (porcentaje / totalValue) * 360f

            // Dibujar sector
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(
                    center.x - radius / 2,
                    center.y - radius / 2
                ),
                size = Size(radius, radius)
            )
            drawArc(
                color = Color(ColorUtils.blendARGB(color.toArgb(), Color.Black.toArgb(), 0.1f)),
                startAngle = startAngle + sweepAngle,
                sweepAngle = 360f - sweepAngle,
                useCenter = true,
                topLeft = Offset(
                    center.x - radius / 2,
                    center.y - radius / 2
                ),
                size = Size(radius, radius)
            )
            // Dibujar texto
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(
                    label,
                    center.x,
                    center.y + radius/2 + 40f,
                    textPaint
                )
            }

            startAngle += sweepAngle
        }
    }

}