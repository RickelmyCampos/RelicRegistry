package com.gilbersoncampos.relicregistry.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.gilbersoncampos.relicregistry.data.model.BodyPosition
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.services.PdfService
import java.io.File
import java.io.FileOutputStream

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
        val effectiveContentX = page.info.pageWidth - 2 * margin
        val effectiveContentY = page.info.pageHeight - 2 * margin
        val reasonX = effectiveContentX / 5
        val reasonY = effectiveContentY / 2
        canvas.drawText(
            "Sítio arqueológico: ${record.archaeologicalSite}",
            calculatePosition(initBorderX, reasonX, position = 0), initBorderY, Paint()
        )
        canvas.drawText(
            "Identificação: ${record.identification}",
            calculatePosition(initBorderX, reasonX, position = 1),
            initBorderY,
            Paint()
        )
        canvas.drawText(
            "Classificação: ${record.identification}",
            calculatePosition(initBorderX, reasonX, position = 2),
            initBorderY,
            Paint()
        )
        canvas.drawText(
            "Localização Prateleira: ${record.shelfLocation}",
            calculatePosition(initBorderX, reasonX, position = 3),
            initBorderY,
            Paint()
        )
        canvas.drawText(
            "Grupo 2: ${record.group}",
            calculatePosition(initBorderX, reasonX, position = 4),
            initBorderY,
            Paint()
        )

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