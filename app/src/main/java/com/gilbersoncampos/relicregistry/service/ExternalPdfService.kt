package com.gilbersoncampos.relicregistry.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.gilbersoncampos.relicregistry.data.enums.TypeFormPDF
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.PDFFormStructureModel
import com.gilbersoncampos.relicregistry.data.services.PdfService
import java.io.File
import java.io.FileOutputStream


class ExternalPdfService(private val context: Context) : PdfService {
    override fun generatePdf(record: CatalogRecordModel, listImages: List<Bitmap>) {
        // Cria um novo documento PDF
        val pdfDocument = PdfDocument()

        // Cria uma página
        val paper = Paper.A4_Portrain
        buildPDF(paper, pdfDocument, record, listImages)
        // generatePdf(paper, pdfDocument, record, listImages)
        // Finaliza a página
        //pdfDocument.finishPage(page)

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

    private fun buildPDF(
        paper: Paper.A4_Portrain,
        pdfDocument: PdfDocument,
        record: CatalogRecordModel,
        listImages: List<Bitmap>
    ) {
        var pageNumber = 1
        var page = newPage(pdfDocument, pageNumber, paper)
        var canvas = page.canvas

        val margin = 21
        val pageXInitWithMargin = 0f + margin
        val pageXEndWithMargin = page.info.pageWidth - margin
        val totalPageWidthWithMargin = page.info.pageWidth - 2 * margin
        val totalPageHeightWithMargin = page.info.pageHeight - 2 * margin
        val pageYInitWithMargin = 0f + margin
        val pageYEndWithMargin = page.info.pageHeight - margin
        val verticalSpace = 1.5f
        var actualLineY = pageYInitWithMargin
        var actualLineX = pageXInitWithMargin
        val columnsInPage = totalPageWidthWithMargin / 4f

        val textSize = 12f
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
        //posicão x + posicao y + tamanho da fonte
        val pdfStructure = record.generatePDFFormStructure()
        var endHeader = pageYInitWithMargin

        val headerContent = record.generateHeader()
        pdfStructure.forEachIndexed { index, pdfFormStructure ->
            val listChunked =
                pdfFormStructure.text?.chunked(columnsInPage.toInt() / 8) ?: listOf()
            //TODO verificar o size
            if (!drawFormYIsValid(
                    pdfFormStructure.type,
                    totalPageHeightWithMargin,
                    when (pdfFormStructure.type) {
                        TypeFormPDF.SELECT -> pdfFormStructure.options?.size ?: 0
                        TypeFormPDF.IMAGE -> listImages.size
                        TypeFormPDF.TEXT -> listChunked.size
                    },
                    actualLineY,
                    verticalSpace,
                    textSize
                )
            ) {
                //Proxima coluna x
                actualLineX += columnsInPage
                actualLineY = endHeader
            }
            //verificar se o x ultrapassa para mandar para a próxima pagina
            if (!drawFormXIsValid(totalPageWidthWithMargin, actualLineX)) {
                //nextPage
                actualLineY = pageYInitWithMargin
                actualLineX = pageXInitWithMargin
                pdfDocument.finishPage(page)
                pageNumber += 1
                page = newPage(pdfDocument, pageNumber, paper)
                canvas = page.canvas
            }
            val isNewPage = actualLineX == pageXInitWithMargin && actualLineY == pageYInitWithMargin
            if (isNewPage) {
                //desenhar header
                canvas.drawRect(
                    RectF(
                        pageXInitWithMargin - 4f,
                        pageYInitWithMargin - textSize - 2f,
                        pageXEndWithMargin + 4f,
                        actualLineY + textSize * (4) + verticalSpace * (4) + 4f
                    ), paintRect
                )
                headerContent.forEach { item ->
                    canvas.drawText(
                        item,
                        actualLineX, actualLineY, paint
                    )
                    actualLineY = nextLine(actualLineY, verticalSpace, textSize)
                    endHeader = actualLineY
                }
            }
            //desenha titulo
            canvas.drawText(
                pdfFormStructure.title,
                actualLineX, actualLineY + textSize, paint
            )
            actualLineY = nextLine(actualLineY, verticalSpace, textSize)
            when (pdfFormStructure.type) {
                TypeFormPDF.SELECT -> {
                    //desenha opçoes
                    actualLineY = drawOptionsWithSelect(
                        pdfFormStructure,
                        actualLineX,
                        textSize,
                        actualLineY,
                        canvas,
                        paintRect,
                        verticalSpace,
                        paint
                    )
                }

                TypeFormPDF.IMAGE -> {
                    val pixelScale = 100
                    listImages.forEach { image ->
                        val scale = (pixelScale).toFloat() / (image.height).toFloat()
                        canvas.drawBitmap(
                            resizeBitmap(image, (image.width * scale).toInt(), 100),
                            actualLineX,
                            actualLineY,
                            null
                        )
                        val positionImage = Rect(
                            actualLineX.toInt(),
                            actualLineY.toInt(),
                            actualLineX.toInt() + (image.width * scale).toInt(),
                            pixelScale + actualLineY.toInt()
                        )
                        canvas.drawBitmap(
                            image,
                            Rect(0, 0, image.width, image.height),
                            positionImage,
                            null
                        )
                        actualLineY += pixelScale.toFloat()
                    }
                }

                TypeFormPDF.TEXT -> {
                    actualLineY = drawSlicedText(
                        listChunked,
                        canvas,
                        textSize,
                        paint,
                        actualLineX,
                        actualLineY,
                        verticalSpace
                    )
                }
            }


        }

        pdfDocument.finishPage(page)
    }

    private fun drawOptionsWithSelect(
        pdfFormStructure: PDFFormStructureModel,
        actualLineX: Float,
        textSize: Float,
        actualLineY: Float,
        canvas: Canvas,
        paintRect: Paint,
        verticalSpace: Float,
        paint: Paint
    ): Float {
        var actualLineY1 = actualLineY
        pdfFormStructure.options?.forEach { option ->
            val recInitX = actualLineX
            val recEndX = actualLineX + textSize
            val recInitY = actualLineY1
            val recEndY = actualLineY1 + textSize
            if (option.selected) {
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
                    crossFloatArray, paintRect
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
            val positionXPosRec = actualLineX + textSize + verticalSpace
            canvas.drawText(
                option.label,
                positionXPosRec, actualLineY1 + textSize, paint
            )
            actualLineY1 = nextLine(actualLineY1, verticalSpace, textSize)
        }
        return actualLineY1
    }

    private fun drawFormXIsValid(totalPageWidthWithMargin: Int, actualLineX: Float): Boolean =
        actualLineX < totalPageWidthWithMargin


    private fun drawFormYIsValid(
        type: TypeFormPDF,
        totalPageHeightWithMargin: Int,
        optionsSize: Int,
        actualLineY: Float,
        verticalSpace: Float,
        textSize: Float
    ): Boolean {
        var actualLineY1 = actualLineY
        when (type) {
            TypeFormPDF.IMAGE -> {
                actualLineY1 += (optionsSize + 1) * (100)
            }

            else -> {
                actualLineY1 += (optionsSize + 1) * (verticalSpace + textSize)
            }
        }

        return actualLineY1 < totalPageHeightWithMargin
    }

    private fun nextLine(
        actualLineY: Float,
        verticalSpace: Float,
        textSize: Float
    ): Float {
        var actualLineY1 = actualLineY
        actualLineY1 += verticalSpace + textSize
        return actualLineY1
    }


    private fun newPage(
        pdfDocument: PdfDocument,
        pageNumber: Int,
        paper: Paper.A4_Portrain
    ): PdfDocument.Page {
        val pageInfo = PdfDocument.PageInfo.Builder(paper.width, paper.height, pageNumber).create()
        return pdfDocument.startPage(pageInfo)
    }

    private fun drawSlicedText(
        listText: List<String>,
        canvas: Canvas,
        textSize: Float,
        paint: Paint,
        actualLineX: Float,
        actualLineY: Float,
        verticalSpace: Float
    ): Float {
        var actualLineYAux = actualLineY
        listText.forEachIndexed { index, textItem ->
            canvas.drawText(
                textItem,
                actualLineX,
                actualLineYAux + textSize,
                paint
            )
            actualLineYAux = nextLine(actualLineYAux, verticalSpace, textSize)
        }
        return actualLineYAux

    }

    override fun getPDF(): File {
        return File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "sample.pdf")
    }

    fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
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