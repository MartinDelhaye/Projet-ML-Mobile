package com.example.mathia.screen.components

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap

@Composable
fun DrawingCanvas(
    onDrawEnd: (Bitmap) -> Unit,
    clearTrigger: Boolean,
    modifier: Modifier = Modifier
) {
    var paths by remember { mutableStateOf(listOf<List<Offset>>()) }
    var currentPath by remember { mutableStateOf(listOf<Offset>()) }

    LaunchedEffect(clearTrigger) {
        paths = listOf()
        currentPath = listOf()
    }

    Canvas(
        modifier = modifier
            .size(280.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentPath = listOf(offset)
                    },
                    onDrag = { change, _ ->
                        currentPath = currentPath + change.position
                    },
                    onDragEnd = {
                        val finalPaths = paths + listOf(currentPath)
                        paths = finalPaths
                        currentPath = listOf()

                        val bitmapWidth = size.width.toInt()
                        val bitmapHeight = size.height.toInt()

                        val bitmap = createBitmap(bitmapWidth, bitmapHeight)
                        val canvas = android.graphics.Canvas(bitmap)

                        canvas.drawColor(android.graphics.Color.BLACK)

                        val paint = android.graphics.Paint().apply {
                            color = android.graphics.Color.WHITE
                            strokeWidth = 20f
                            style = android.graphics.Paint.Style.STROKE
                            strokeCap = android.graphics.Paint.Cap.ROUND
                            strokeJoin = android.graphics.Paint.Join.ROUND
                            isAntiAlias = true
                        }

                        finalPaths.forEach { path ->
                            if (path.size > 1) {
                                val androidPath = android.graphics.Path().apply {
                                    moveTo(path.first().x, path.first().y)
                                    path.drop(1).forEach { lineTo(it.x, it.y) }
                                }
                                canvas.drawPath(androidPath, paint)
                            }
                        }

                        onDrawEnd(bitmap)
                    }
                )
            }
    ) {
        // Fond noir style ardoise pour l'affichage
        drawRect(color = Color.Black)

        // Tracés terminés en blanc
        paths.forEach { path ->
            if (path.size > 1) {
                val drawPath = Path().apply {
                    moveTo(path.first().x, path.first().y)
                    path.drop(1).forEach { lineTo(it.x, it.y) }
                }
                drawPath(
                    path = drawPath,
                    color = Color.White,
                    style = Stroke(
                        width = 20f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }

        // Tracé en cours en blanc
        if (currentPath.size > 1) {
            val drawPath = Path().apply {
                moveTo(currentPath.first().x, currentPath.first().y)
                currentPath.drop(1).forEach { lineTo(it.x, it.y) }
            }
            drawPath(
                path = drawPath,
                color = Color.White,
                style = Stroke(
                    width = 20f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}