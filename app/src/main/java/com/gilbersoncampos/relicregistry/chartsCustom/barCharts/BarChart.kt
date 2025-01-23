package com.gilbersoncampos.relicregistry.chartsCustom.barCharts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarChart() {
    val barData = listOf(10f, 40f, 20f, 60f, 30f)
    val barLabel = listOf("1", "1", "1", "1", "1")
    val textMeasurer = rememberTextMeasurer()
    val maxValue=barData.max()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(300.dp)
    ) {
        val barWidth = size.width / (barData.size * 2)
        val scale = (size.height/maxValue)-4
        barData.forEachIndexed { index, value ->
            val heightBar = value * scale
            val heightFloorBar = 50
            drawRect(
                color = Color.Blue,
                topLeft = androidx.compose.ui.geometry.Offset(
                    x = index * barWidth * 2 + barWidth / 2,
                    y = size.height - heightFloorBar - heightBar
                ),
                size = Size(barWidth, heightBar)
            )
            drawIntoCanvas { canvas ->
                val label = barLabel[index]
                canvas.nativeCanvas.drawText(
                    label,
                    index * barWidth * 2 + barWidth / 2 + barWidth / 4,
                    size.height - 5,  // Posição abaixo da barra
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BarChartPreview() {
    BarChart()
}