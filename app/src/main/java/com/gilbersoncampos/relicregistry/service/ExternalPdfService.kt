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
import com.gilbersoncampos.relicregistry.data.model.AccessoryType
import com.gilbersoncampos.relicregistry.data.model.BodyPosition
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.Condition
import com.gilbersoncampos.relicregistry.data.model.DecorationLocation
import com.gilbersoncampos.relicregistry.data.model.DecorationType
import com.gilbersoncampos.relicregistry.data.model.Firing
import com.gilbersoncampos.relicregistry.data.model.GeneralBodyShape
import com.gilbersoncampos.relicregistry.data.model.Genitalia
import com.gilbersoncampos.relicregistry.data.model.LowerLimbs
import com.gilbersoncampos.relicregistry.data.model.ManufacturingMarks
import com.gilbersoncampos.relicregistry.data.model.ManufacturingTechnique
import com.gilbersoncampos.relicregistry.data.model.PaintColor
import com.gilbersoncampos.relicregistry.data.model.PlasticDecoration
import com.gilbersoncampos.relicregistry.data.model.StatueType
import com.gilbersoncampos.relicregistry.data.model.SurfaceTreatment
import com.gilbersoncampos.relicregistry.data.model.Temper
import com.gilbersoncampos.relicregistry.data.model.UpperLimbs
import com.gilbersoncampos.relicregistry.data.model.UsageMarks
import com.gilbersoncampos.relicregistry.data.model.Uses
import com.gilbersoncampos.relicregistry.data.services.PdfService
import java.io.File
import java.io.FileOutputStream

class ExternalPdfService(private val context: Context) : PdfService {
    override fun generatePdf(record: CatalogRecordModel, listImages: List<Bitmap>) {
        // Cria um novo documento PDF
        val pdfDocument = PdfDocument()

        // Cria uma página
        val paper = Paper.A4_Portrain
        val page = generatePdf(paper, pdfDocument, record, listImages)
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

    private fun generatePdf(
        paper: Paper.A4_Portrain,
        pdfDocument: PdfDocument,
        record: CatalogRecordModel,
        listImages: List<Bitmap>
    ): PdfDocument.Page? {
        val pageInfo1 = PdfDocument.PageInfo.Builder(paper.width, paper.height, 1).create()
        val page1 = pdfDocument.startPage(pageInfo1)

        // Desenhar no Canvas da página
        generatePDFPage1Default(page1, record, listImages)
        pdfDocument.finishPage(page1)

        val pageInfo2 = PdfDocument.PageInfo.Builder(paper.width, paper.height, 2).create()
        val page2 = pdfDocument.startPage(pageInfo2)
        generatePDFPage2Default(page2, record, listImages)
        pdfDocument.finishPage(page2)
        return page1
    }

    private fun generatePDFPage1Default(
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
        val reasonX = page.info.pageWidth / 4
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
        //ROW 1

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
            calculateYPosition(yBodyInitPosition, StatueType.entries.count() + 1, textSize, spacer),
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
            calculateYPosition(
                yBodyInitPosition,
                StatueType.entries.count() + Condition.entries.count() + 2,
                textSize,
                spacer
            ),
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
            calculateYPosition(
                yBodyInitPosition,
                StatueType.entries.count() + Condition.entries.count() + GeneralBodyShape.entries.count() + 3,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            UpperLimbs.entries,
            record.upperLimbs
        )
        //ROW 2
        val row2xPosition = calculateXPosition(initBorderX, reasonX, 1)
        generateOptions(
            "Membros Inferiores",
            canvas,
            row2xPosition,
            yBodyInitPosition,
            textSize,
            spacer,
            paint,
            paintRect,
            LowerLimbs.entries,
            record.lowerLimbs
        )
        generateOptions(
            "Fabricação de Genitália",
            canvas,
            row2xPosition,
            calculateYPosition(yBodyInitPosition, LowerLimbs.entries.count() + 1, textSize, spacer),
            textSize,
            spacer,
            paint,
            paintRect,
            Genitalia.entries,
            record.genitalia?.let { listOf(it) } ?: emptyList()
        )
        generateOptions(
            "Queima",
            canvas,
            row2xPosition,
            calculateYPosition(
                yBodyInitPosition,
                LowerLimbs.entries.count() + Genitalia.entries.count() + 2,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            Firing.entries,
            record.firing
        )
        generateOptions(
            "Antiplástico",
            canvas,
            row2xPosition,
            calculateYPosition(
                yBodyInitPosition,
                LowerLimbs.entries.count() + Genitalia.entries.count() + Firing.entries.count() + 3,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            Temper.entries,
            record.temper
        )
        generateOptions(
            "Técnica de fabricação",
            canvas,
            row2xPosition,
            calculateYPosition(
                yBodyInitPosition,
                LowerLimbs.entries.count() + Genitalia.entries.count() + Firing.entries.count() + Temper.entries.count() + 4,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            ManufacturingTechnique.entries,
            record.manufacturingTechnique
        )
        //ROW 3
        val row3xPosition = calculateXPosition(initBorderX, reasonX, 2)
        generateOptions(
            "Marcas de fabricação",
            canvas,
            row3xPosition,
            yBodyInitPosition,
            textSize,
            spacer,
            paint,
            paintRect,
            ManufacturingMarks.entries,
            record.manufacturingMarks
        )
        generateOptions(
            "Marcas de Uso",
            canvas,
            row3xPosition,
            calculateYPosition(
                yBodyInitPosition,
                ManufacturingMarks.entries.count() + 1,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            UsageMarks.entries,
            record.usageMarks
        )
        generateOptions(
            "Tratamento de Sup. (I.T.)",
            canvas,
            row3xPosition,
            calculateYPosition(
                yBodyInitPosition,
                ManufacturingMarks.entries.count() + UsageMarks.entries.count() + 2,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            SurfaceTreatment.entries,
            record.surfaceTreatmentInternal
        )
        generateOptions(
            "Tratamento de Sup. (E.T.)",
            canvas,
            row3xPosition,
            calculateYPosition(
                yBodyInitPosition,
                ManufacturingMarks.entries.count() + UsageMarks.entries.count() + SurfaceTreatment.entries.count() + 3,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            SurfaceTreatment.entries,
            record.surfaceTreatmentExternal
        )
        generateOptions(
            "Decoração",
            canvas,
            row3xPosition,
            calculateYPosition(
                yBodyInitPosition,
                ManufacturingMarks.entries.count() + UsageMarks.entries.count() + SurfaceTreatment.entries.count() + SurfaceTreatment.entries.count() + 4,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            DecorationLocation.entries,
            record.decorationLocation?.let { listOf(it) } ?: emptyList()
        )
        //ROW 4
        val row4xPosition = calculateXPosition(initBorderX, reasonX, 3)
        generateOptions(
            "Tipo de decoração",
            canvas,
            row4xPosition,
            yBodyInitPosition,
            textSize,
            spacer,
            paint,
            paintRect,
            DecorationType.entries,
            record.decorationType
        )
        generateOptions(
            "Cor da pintura (F.I)",
            canvas,
            row4xPosition,
            calculateYPosition(
                yBodyInitPosition,
                DecorationType.entries.count() + 1,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            PaintColor.entries,
            record.internalPaintColor
        )
        generateOptions(
            "Cor da pintura (F.E)",
            canvas,
            row4xPosition,
            calculateYPosition(
                yBodyInitPosition,
                DecorationType.entries.count() + PaintColor.entries.count() + 2,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            PaintColor.entries,
            record.externalPaintColor
        )
        generateOptions(
            "Decoração plástica",
            canvas,
            row4xPosition,
            calculateYPosition(
                yBodyInitPosition,
                DecorationType.entries.count() + PaintColor.entries.count() + PaintColor.entries.count() + 3,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            PlasticDecoration.entries,
            record.plasticDecoration
        )

        //End Body
    }

    private fun generatePDFPage2Default(
        page: PdfDocument.Page,
        record: CatalogRecordModel,
        listImages: List<Bitmap>
    ) {
        val canvas = page.canvas

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
        val reasonX = page.info.pageWidth / 4
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
        //ROW 1
        generateOptions(
            "Outros atributos formais",
            canvas,
            initBorderX,
            calculateYPosition(yBodyInitPosition, 0, textSize, spacer),
            textSize,
            spacer,
            paint,
            paintRect,
            AccessoryType.entries,
            record.otherFormalAttributes
        )
        generateOptions(
            "Posição corporal",
            canvas,
            initBorderX,
            calculateYPosition(
                yBodyInitPosition,
                AccessoryType.entries.count() + 1,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            BodyPosition.entries,
            record.bodyPosition
        )
        generateOptions(
            "Usos",
            canvas,
            initBorderX,
            calculateYPosition(
                yBodyInitPosition,
                AccessoryType.entries.count() + BodyPosition.entries.count() + 2,
                textSize,
                spacer
            ),
            textSize,
            spacer,
            paint,
            paintRect,
            Uses.entries,
            record.uses
        )
        val row2xPosition = calculateXPosition(initBorderX, reasonX, 1)
        canvas.drawText(
            "Observações:",
            row2xPosition, calculateYPosition(yBodyInitPosition, 0, textSize, spacer), paint
        )
        val text =
            "Observações:\n TEasdasdhgj \n asdniasnduaihb asbdjbaiuusag diasgdui gauisgdyiaguydgasuy dguyasgdu as gdkugaskdy gasdgasy gdil asgdui asglidgasui gduiasgduiasgdiasgdigy as"
        val listChuncked = text.chunked(reasonX / 8)
        drawSlicedText(
            listChuncked,
            canvas,
            row2xPosition,
            yBodyInitPosition,
            textSize,
            spacer,
            paint
        )
        val scale = (100).toFloat() / (listImages[0].height).toFloat()
        canvas.drawBitmap(
            resizeBitmap(listImages[0], (listImages[0].width * scale).toInt(), 100),
            row2xPosition,
            calculateYPosition(yBodyInitPosition, listChuncked.count(), textSize, spacer),
            null
        )
        val positionImage = Rect(
            row2xPosition.toInt(),
            calculateYPosition(yBodyInitPosition, listChuncked.count(), textSize, spacer).toInt(),
            row2xPosition.toInt() + (listImages[0].width * scale).toInt(),
            100 + calculateYPosition(
                yBodyInitPosition,
                listChuncked.count(),
                textSize,
                spacer
            ).toInt()
        )
        canvas.drawBitmap(
            listImages[0],
            Rect(0, 0, listImages[0].width, listImages[0].height),
            positionImage,
            null
        )
        //End Body
    }

    private fun drawSlicedText(
        listText: List<String>,
        canvas: Canvas,
        row2xPosition: Float,
        yBodyInitPosition: Float,
        textSize: Float,
        spacer: Float,
        paint: Paint
    ) {

        listText.forEachIndexed { index, textItem ->
            canvas.drawText(
                textItem,
                row2xPosition,
                calculateYPosition(yBodyInitPosition, index + 1, textSize, spacer),
                paint
            )
        }

    }


    override fun getPDF(): File {
        return File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "sample.pdf")
    }

    private fun calculateXPosition(initBorderX: Float, reasonX: Int, position: Int) =
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