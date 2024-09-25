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
import com.gilbersoncampos.relicregistry.extensions.getNameTranslated
import java.io.File
import java.io.FileOutputStream
import kotlin.enums.EnumEntries

class ExternalPdfService(private val context: Context) : PdfService {
    override fun generatePdf(record: CatalogRecordModel, listImages: List<Bitmap>) {
        // Cria um novo documento PDF
        val pdfDocument = PdfDocument()

        // Cria uma página
        val paper = Paper.A4_Portrain
        generatePdf(paper, pdfDocument, record, listImages)
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
        val row1listOptions: List<Triple<String, EnumEntries<out Enum<*>>, List<Enum<*>>>> = listOf(
            Triple(
                "Artefato",
                StatueType.entries,
                record.statueType?.let { listOf(it) } ?: emptyList()
            ),
            Triple(
                "Condição",
                Condition.entries,
                record.condition?.let { listOf(it) } ?: emptyList()
            ),
            Triple(
                "Forma Geral do Corpo",
                GeneralBodyShape.entries,
                record.generalBodyShape?.let { listOf(it) } ?: emptyList()
            ),
            Triple("Membros Superiores", UpperLimbs.entries, record.upperLimbs),
        )

        generateRowByOptions(
            row1listOptions,
            canvas,
            initBorderX,
            yBodyInitPosition,
            textSize,
            spacer,
            paint,
            paintRect
        )

        //ROW 2
       val row2listOptions : List<Triple<String, EnumEntries<out Enum<*>>, List<Enum<*>>>> = listOf(
           Triple(
               "Membros Inferiores",
               LowerLimbs.entries,
               record.lowerLimbs
           ),
           Triple(
               "Fabricação de Genitália",
               Genitalia.entries,
               record.genitalia?.let { listOf(it) } ?: emptyList()
           ),
           Triple(
               "Queima",
               Firing.entries,
               record.firing
           ),
           Triple("Antiplástico", Temper.entries,
               record.temper),
           Triple("Técnica de fabricação", ManufacturingTechnique.entries,
               record.manufacturingTechnique),
       )
        val row2xPosition = calculateXPosition(initBorderX, reasonX, 1)
        generateRowByOptions(
            row2listOptions,
            canvas,
            row2xPosition,
            yBodyInitPosition,
            textSize,
            spacer,
            paint,
            paintRect
        )
        //ROW 3
        val row3listOptions : List<Triple<String, EnumEntries<out Enum<*>>, List<Enum<*>>>> = listOf(
            Triple(
                "Marcas de fabricação",
                ManufacturingMarks.entries,
                record.manufacturingMarks
            ),
            Triple(
                "Marcas de Uso",
                UsageMarks.entries,
                record.usageMarks
            ),
            Triple(
                "Tratamento de Sup. (I.T.)",
                SurfaceTreatment.entries,
                record.surfaceTreatmentInternal
            ),
            Triple("Tratamento de Sup. (E.T.)", SurfaceTreatment.entries,
                record.surfaceTreatmentExternal),
            Triple("Decoração", DecorationLocation.entries,
                record.decorationLocation?.let { listOf(it) } ?: emptyList()),
        )
        val row3xPosition = calculateXPosition(initBorderX, reasonX, 2)
        generateRowByOptions(
            row3listOptions,
            canvas,
            row3xPosition,
            yBodyInitPosition,
            textSize,
            spacer,
            paint,
            paintRect
        )
        //ROW 4
        val row4listOptions : List<Triple<String, EnumEntries<out Enum<*>>, List<Enum<*>>>> = listOf(
            Triple(
                "Tipo de decoração",
                DecorationType.entries,
                record.decorationType
            ),
            Triple(
                "Cor da pintura (F.I)",
                PaintColor.entries,
                record.internalPaintColor
            ),
            Triple(
                "Cor da pintura (F.E)",
                PaintColor.entries,
                record.externalPaintColor
            ),
            Triple("Decoração plástica", PlasticDecoration.entries,
                record.plasticDecoration),
        )
        val row4xPosition = calculateXPosition(initBorderX, reasonX, 3)
        generateRowByOptions(
            row4listOptions,
            canvas,
            row4xPosition,
            yBodyInitPosition,
            textSize,
            spacer,
            paint,
            paintRect
        )
        //End Body
    }

    private fun generateRowByOptions(
        listOptionsRow1: List<Triple<String, EnumEntries<out Enum<*>>, List<Enum<*>>>>,
        canvas: Canvas,
        initialPositionX: Float,
        initialPositionY: Float,
        textSize: Float,
        spacer: Float,
        paint: Paint,
        paintRect: Paint
    ) {
        listOptionsRow1.forEachIndexed { index, option ->
            val position = listOptionsRow1.foldIndexed(0) { i, acc, op ->
                if (i < index) {
                    acc + op.second.size
                } else {
                    acc
                }
            } + index + 1
            generateOptionsTwo(
                canvas,
                option,
                initialPositionX,
                calculateYPosition(initialPositionY, position, textSize, spacer),
                textSize,
                spacer,
                paint,
                paintRect
            )
        }
    }

    private fun generateOptionsTwo(
        canvas: Canvas,
        option: Triple<String, EnumEntries<out Enum<*>>, List<Enum<*>>>,
        initBorderX: Float,
        yBodyInitPosition: Float,
        textSize: Float,
        spacer: Float,
        paint: Paint,
        paintRect: Paint
    ) {
        canvas.drawText(
            option.first,
            initBorderX, calculateYPosition(yBodyInitPosition, 0, textSize, spacer), paint
        )
        val yInitPositionCheckOptions = calculateYPosition(yBodyInitPosition, 1, textSize, spacer)
        for (i in option.second.indices) {
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

            if (option.third.contains(option.second[i])) {
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
                option.second[i].getNameTranslated(),
                initBorderX + 12f,
                calculateYPosition(yInitPositionCheckOptions, i, textSize, spacer),
                paint
            )
        }
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
        val row1listOptions : List<Triple<String, EnumEntries<out Enum<*>>, List<Enum<*>>>> = listOf(
            Triple(
                "Outros atributos formais",
                AccessoryType.entries,
                record.otherFormalAttributes
            ),
            Triple(
                "Posição corporal",
                BodyPosition.entries,
                record.bodyPosition
            ),
            Triple(
                "Usos",
                Uses.entries,
                record.uses
            ),
        )
        val row1xPosition = initBorderX

        generateRowByOptions(
            row1listOptions,
            canvas,
            row1xPosition,
            yBodyInitPosition,
            textSize,
            spacer,
            paint,
            paintRect
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