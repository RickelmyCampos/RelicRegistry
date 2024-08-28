package com.gilbersoncampos.relicregistry.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.services.PdfService
import java.io.File
import java.io.FileOutputStream
class ExternalPdfService(private val context: Context): PdfService {
    override fun generatePdf(record:CatalogRecordModel,listImages: List<Bitmap>) {
        // Cria um novo documento PDF
        val pdfDocument = PdfDocument()

        // Cria uma página
        val pageInfo = PdfDocument.PageInfo.Builder(842, 595, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        // Desenhar no Canvas da página
        val canvas = page.canvas
        val bitmap=createBitmap()
        val scale=(500).toFloat()/(listImages[0].height).toFloat()
        canvas.drawBitmap(resizeBitmap(listImages[0],(listImages[0].width*scale).toInt(),500),0f,0f,null)
        //canvas.drawText("Numeração: ${record.identification}", 80f, 50f, android.graphics.Paint())

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

}
