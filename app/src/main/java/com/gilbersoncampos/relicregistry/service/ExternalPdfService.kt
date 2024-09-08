package com.gilbersoncampos.relicregistry.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import android.icu.text.CaseMap.Title
import android.os.Environment
import com.gilbersoncampos.relicregistry.data.model.BodyPosition
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.Condition
import com.gilbersoncampos.relicregistry.data.model.GeneralBodyShape
import com.gilbersoncampos.relicregistry.data.model.StatueType
import com.gilbersoncampos.relicregistry.data.model.UpperLimbs
import com.gilbersoncampos.relicregistry.data.services.PdfService
import java.io.File
import java.io.FileOutputStream
import kotlin.enums.EnumEntries

class ExternalPdfService(private val context: Context) : PdfService {
    override fun generatePdf(record: CatalogRecordModel, listImages: List<Bitmap>) {
        // Cria um novo documento PDF
        val pdfDocument = PdfDocument()

        // Cria uma página
        val paper = Paper.A4_Portrain
        val pageInfo = PdfDocument.PageInfo.Builder(paper.width, paper.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        // Desenhar no Canvas da página
        GeneratePDFDefault(page, record, listImages)
        // Finaliza a página
        pdfDocument.finishPage(page)

        // Especifica o caminho onde o PDF será salvo
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "sample.pdf")

        // Salva o documento em um arquivo
        try {
            pdfDocument.writeTo(FileOutputStream(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // Fecha o documento PDF
        pdfDocument.close()
    }

    override fun getPDF(): File {
        return File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "sample.pdf")
    }

    private fun calculatePosition(initBorderX: Float, reasonX: Int, position: Int) =
        initBorderX + (reasonX * position)

    private fun calculateYPosition(initY: Float, position: Int, textSize: Float, spacer: Float) =
        initY + (position * (textSize + spacer))

    fun createBitmap(): Bitmap {
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        canvas.drawColor(Color.WHITE)

        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.FILL
            textSize = 50f
        }

        canvas.drawCircle(width / 2f, height / 2f, 100f, paint)

        paint.color = Color.BLACK
        canvas.drawText("Hello, Bitmap!", 100f, height - 100f, paint)

        return bitmap
    }

    fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private fun GeneratePDFDefault(
        page: PdfDocument.Page,
        record: CatalogRecordModel,
        listImages: List<Bitmap>
    ) {
        val canvas = page.canvas
        val scale = (500).toFloat() / (listImages[0].height).toFloat()
        // canvas.drawBitmap(resizeBitmap(listImages[0],(listImages[0].width*scale).toInt(),500),0f,0f,null)
        //firstLine
        val margin = 21
        val initBorderX = margin + 0f
        val initBorderY = margin + 0f
        val textSize = 12f
        val spacer = 1.5f
        val paint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
            this.textSize = textSize
        }
        val paintRect = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
        //Start Header
        //Rectangle Header
        canvas.drawRect(
            RectF(
                initBorderX - 2f,
                initBorderY - textSize - 2f,
                page.info.pageWidth - 2f,
                calculateYPosition(initBorderY, 4, textSize, spacer) + 2f
            ), paintRect
        )

        canvas.drawText(
            "Sítio arqueológico: ${record.archaeologicalSite}",
            initBorderX, calculateYPosition(initBorderY, 0, textSize, spacer), paint
        )
        canvas.drawText(
            "Identificação: ${record.identification}",
            initBorderX, calculateYPosition(initBorderY, 1, textSize, spacer), paint
        )
        canvas.drawText(
            "Classificação: ${record.classification}",
            initBorderX, calculateYPosition(initBorderY, 2, textSize, spacer), paint
        )
        canvas.drawText(
            "Localização Prateleira: ${record.shelfLocation}",
            initBorderX, calculateYPosition(initBorderY, 3, textSize, spacer), paint
        )
        canvas.drawText(
            "Grupo: ${record.group}",
            initBorderX, calculateYPosition(initBorderY, 4, textSize, spacer), paint
        )
        //End Header

        //Start Body
        //Rectangle Body
        val yBodyInitPosition =
            calculateYPosition(initBorderY, 4, textSize, spacer) + textSize + 10f
        canvas.drawRect(
            RectF(
                initBorderX - 2f,
                yBodyInitPosition - 2f - textSize,
                page.info.pageWidth - 2f,
                page.info.pageHeight - 2f
            ), paintRect
        )
        generateOptions(
            "Artefato",
            canvas,
            initBorderX,
            yBodyInitPosition,
            textSize,
            spacer,
            paint,
            paintRect,
            StatueType.entries,
            record.statueType?.let { listOf(it) } ?: emptyList()
        )
        generateOptions(
            "Condição",
            canvas,
            initBorderX,
            calculateYPosition(yBodyInitPosition, StatueType.entries.count()+1, textSize, spacer),
            textSize,
            spacer,
            paint,
            paintRect,
            Condition.entries,
            record.condition?.let { listOf(it) } ?: emptyList()
        )
        generateOptions(
            "Forma Geral do Corpo",
            canvas,
            initBorderX,
            calculateYPosition(yBodyInitPosition, StatueType.entries.count()+Condition.entries.count()+2, textSize, spacer),
            textSize,
            spacer,
            paint,
            paintRect,
            GeneralBodyShape.entries,
            record.generalBodyShape?.let { listOf(it) } ?: emptyList()
        )
        generateOptions(
            "Membros Superiores",
            canvas,
            initBorderX,
            calculateYPosition(yBodyInitPosition, StatueType.entries.count()+Condition.entries.count()+GeneralBodyShape.entries.count()+3, textSize, spacer),
            textSize,
            spacer,
            paint,
            paintRect,
            UpperLimbs.entries,
            record.upperLimbs
        )


        //End Body
    }

    private fun <T : Enum<T>> generateOptions(
        title: String,
        canvas: Canvas,
        initBorderX: Float,
        yBodyInitPosition: Float,
        textSize: Float,
        spacer: Float,
        paint: Paint,
        paintRect: Paint,
        options: List<T>,
        listSelectedOptions: List<T>
    ) {

        canvas.drawText(
            title,
            initBorderX, calculateYPosition(yBodyInitPosition, 0, textSize, spacer), paint
        )

        val yInitPositionCheckOptions = calculateYPosition(yBodyInitPosition, 1, textSize, spacer)
        for (i in options.indices) {
            val recInitX = initBorderX
            val recInitY = calculateYPosition(
                yInitPositionCheckOptions,
                i,
                textSize,
                spacer
            ) - textSize + 2f
            val recEndX = initBorderX + 10f
            val recEndY = calculateYPosition(
                yInitPositionCheckOptions,
                i,
                textSize,
                spacer
            ) - textSize + 12f

            if (listSelectedOptions.contains(options[i])) {
                //floatArray(x,y,endx,endy)
                val crossFloatArray = floatArrayOf(
                    recInitX,
                    recInitY,
                    recEndX,
                    recEndY,
                    recEndX,
                    recInitY,
                    recInitX,
                    recEndY
                )
                canvas.drawLines(
                    crossFloatArray, paint
                )
            }
            canvas.drawRect(
                RectF(
                    recInitX,
                    recInitY,
                    recEndX,
                    recEndY
                ), paintRect
            )
            canvas.drawText(
                options[i].name,
                initBorderX + 12f,
                calculateYPosition(yInitPositionCheckOptions, i, textSize, spacer),
                paint
            )
        }
    }
}

/**
 * Classe que representa um papel
 * @param width Largura do papel em PostScript
 * @param height Altura do papel em PostScript
 */
sealed class Paper(val width: Int, val height: Int) {
    data object A4 : Paper(595, 842)
    data object A4_Portrain : Paper(842, 595)
}