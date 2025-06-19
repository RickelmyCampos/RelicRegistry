package com.gilbersoncampos.relicregistry.data.model

import com.gilbersoncampos.relicregistry.data.enums.AccessoryType
import com.gilbersoncampos.relicregistry.data.enums.BodyPosition
import com.gilbersoncampos.relicregistry.data.enums.Condition
import com.gilbersoncampos.relicregistry.data.enums.DecorationLocation
import com.gilbersoncampos.relicregistry.data.enums.DecorationType
import com.gilbersoncampos.relicregistry.data.enums.FilterEnum
import com.gilbersoncampos.relicregistry.data.enums.Firing
import com.gilbersoncampos.relicregistry.data.enums.GeneralBodyShape
import com.gilbersoncampos.relicregistry.data.enums.Genitalia
import com.gilbersoncampos.relicregistry.data.enums.LowerLimbs
import com.gilbersoncampos.relicregistry.data.enums.ManufacturingMarks
import com.gilbersoncampos.relicregistry.data.enums.ManufacturingTechnique
import com.gilbersoncampos.relicregistry.data.enums.PaintColor
import com.gilbersoncampos.relicregistry.data.enums.PaintColorExternal
import com.gilbersoncampos.relicregistry.data.enums.PaintColorInternal
import com.gilbersoncampos.relicregistry.data.enums.PlasticDecoration
import com.gilbersoncampos.relicregistry.data.enums.StatueType
import com.gilbersoncampos.relicregistry.data.enums.SurfaceTreatment
import com.gilbersoncampos.relicregistry.data.enums.SurfaceTreatmentExternal
import com.gilbersoncampos.relicregistry.data.enums.SurfaceTreatmentInternal
import com.gilbersoncampos.relicregistry.data.enums.Temper
import com.gilbersoncampos.relicregistry.data.enums.TypeFormPDF
import com.gilbersoncampos.relicregistry.data.enums.UpperLimbs
import com.gilbersoncampos.relicregistry.data.enums.UsageMarks
import com.gilbersoncampos.relicregistry.data.enums.Uses
import com.gilbersoncampos.relicregistry.extensions.getNameTranslated
import java.time.LocalDateTime
import java.util.Date

data class CatalogRecordModel(
    val id: Long,
    val listImages: List<String> = listOf(),
    val archaeologicalSite: String,
    val identification: String,
    val classification: String,
    val shelfLocation: String,
    val group: String,
    // Typology
    val statueType: StatueType? = null,
    val condition: Condition? = null,
    val generalBodyShape: GeneralBodyShape? = null,
    // Portions
    val upperLimbs: List<UpperLimbs> = listOf(),
    val lowerLimbs: List<LowerLimbs> = listOf(),
    // Genitalia
    val genitalia: Genitalia? = null,
    // Dimensions
    val length: Float? = null,
    val width: Float? = null,
    val height: Float? = null,
    val weight: Float? = null,
    // Technology
    val firing: List<Firing> = listOf(),
    val temper: List<Temper> = listOf(),
    val manufacturingTechnique: List<ManufacturingTechnique> = listOf(),
    val manufacturingMarks: List<ManufacturingMarks> = listOf(),
    val usageMarks: List<UsageMarks> = listOf(),
    val surfaceTreatmentInternal: List<SurfaceTreatment> = listOf(),
    val surfaceTreatmentExternal: List<SurfaceTreatment> = listOf(),
    // Decoration
    val decorationLocation: DecorationLocation? = null,
    val decorationType: List<DecorationType> = listOf(),
    val internalPaintColor: List<PaintColor> = listOf(),
    val externalPaintColor: List<PaintColor> = listOf(),
    val plasticDecoration: List<PlasticDecoration> = listOf(),
    // Other Formal Attributes
    val otherFormalAttributes: List<AccessoryType> = listOf(),
    // Body Position
    val bodyPosition: List<BodyPosition> = listOf(),
    // Uses
    val uses: List<Uses> = listOf(),
    // Observations
    val observations: String,
    val hasDecoration: Boolean = false,
    val createdAt: LocalDateTime? = null,
    val idRemote: String? = null
) {
    fun generateHeader(): List<String> {
        val result = mutableListOf<String>()
        result.add("Sitio Arqueológico: $archaeologicalSite")
        result.add("Identificação: $identification")
        result.add("Classificação: $classification")
        result.add("Localização prateleira: $shelfLocation")
        result.add("Grupo: $group")
        return result
    }

    fun generatePDFFormStructure(): List<PDFFormStructureModel> {
        val results = mutableListOf<PDFFormStructureModel>()
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.IMAGE,
                "Fotos:",
            )
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.STATUE_TYPE.translatedName,
                options = StatueType.entries.map { entry ->
                    val selected = entry == statueType
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.CONDITION.translatedName,
                options = Condition.entries.map { entry ->
                    val selected = entry == condition
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.GENERAL_BODY_SHAPE.translatedName,
                options = GeneralBodyShape.entries.map { entry ->
                    val selected = entry == generalBodyShape
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.UPPER_LIMBS.translatedName,
                options = UpperLimbs.entries.map { entry ->
                    val selected = upperLimbs.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.LOWER_LIMBS.translatedName, options = LowerLimbs.entries.map { entry ->
                    val selected = lowerLimbs.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.GENITALIA.translatedName, options = Genitalia.entries.map { entry ->
                    val selected = entry == genitalia
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.FIRING.translatedName, options = Firing.entries.map { entry ->
                    val selected = firing.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.TEMPER.translatedName, options = Temper.entries.map { entry ->
                    val selected = temper.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.MANUFACTURING_TECHNIQUE.translatedName,
                options = ManufacturingTechnique.entries.map { entry ->
                    val selected = manufacturingTechnique.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.MANUFACTURING_MARKS.translatedName,
                options = ManufacturingMarks.entries.map { entry ->
                    val selected = manufacturingMarks.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.USAGE_MARKS.translatedName, options = UsageMarks.entries.map { entry ->
                    val selected = usageMarks.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.SURFACE_TREATMENT_INTERNAL.translatedName,
                options = SurfaceTreatment.entries.map { entry ->
                    val selected = surfaceTreatmentInternal.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.SURFACE_TREATMENT_EXTERNAL.translatedName,
                options = SurfaceTreatment.entries.map { entry ->
                    val selected = surfaceTreatmentExternal.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.DECORATION_LOCATION.translatedName,
                options = DecorationLocation.entries.map { entry ->
                    val selected = decorationLocation == entry
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.DECORATION_TYPE.translatedName,
                options = DecorationType.entries.map { entry ->
                    val selected = decorationType.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.PAINT_COLOR_INTERNAL.translatedName,
                options = PaintColor.entries.map { entry ->
                    val selected = internalPaintColor.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.PAINT_COLOR_EXTERNAL.translatedName,
                options = PaintColor.entries.map { entry ->
                    val selected = externalPaintColor.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.PLASTIC_DECORATION.translatedName,
                options = PlasticDecoration.entries.map { entry ->
                    val selected = plasticDecoration.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.ACCESSORY_TYPE.translatedName,
                options = AccessoryType.entries.map { entry ->
                    val selected = otherFormalAttributes.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.BODY_POSITION.translatedName,
                options = BodyPosition.entries.map { entry ->
                    val selected = bodyPosition.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                FilterEnum.USES.translatedName, options = Uses.entries.map { entry ->
                    val selected = uses.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.TEXT,
                "Observações: ",
                text = observations
            )
        )

        return results

    }
}
