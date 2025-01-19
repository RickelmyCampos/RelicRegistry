package com.gilbersoncampos.relicregistry.data.model

import com.gilbersoncampos.relicregistry.data.enums.TypeFormPDF
import com.gilbersoncampos.relicregistry.extensions.getNameTranslated

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
                "Artefato",
                options = StatueType.entries.map { entry ->
                    val selected=entry==statueType
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Condição",
                options = Condition.entries.map { entry ->
                    val selected=entry==condition
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Forma Geral do Corpo",
                options = GeneralBodyShape.entries.map { entry ->
                    val selected=entry==generalBodyShape
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Membros Superiores",
                options = UpperLimbs.entries.map { entry ->
                    val selected=upperLimbs.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Membros Inferiores",
                options = LowerLimbs.entries.map { entry ->
                    val selected=lowerLimbs.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Fabricação de Genitália",
                options = Genitalia.entries.map { entry ->
                    val selected=entry==genitalia
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Queima",
                options = Firing.entries.map { entry ->
                    val selected=firing.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Antiplástico",
                options = Temper.entries.map { entry ->
                    val selected=temper.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Técnica de fabricação",
                options = ManufacturingTechnique.entries.map { entry ->
                    val selected=manufacturingTechnique.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Marcas de fabricação",
                options = ManufacturingMarks.entries.map { entry ->
                    val selected=manufacturingMarks.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Marcas de Uso",
                options = UsageMarks.entries.map { entry ->
                    val selected=usageMarks.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Tratamento de Sup. (I.T.)",
                options = SurfaceTreatment.entries.map { entry ->
                    val selected=surfaceTreatmentInternal.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Tratamento de Sup. (E.T.)",
                options = SurfaceTreatment.entries.map { entry ->
                    val selected=surfaceTreatmentExternal.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Decoração",
                options = DecorationLocation.entries.map { entry ->
                    val selected=decorationLocation==entry
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Tipo de decoração",
                options = DecorationType.entries.map { entry ->
                    val selected=decorationType.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Cor da pintura (F.I)",
                options = PaintColor.entries.map { entry ->
                    val selected=internalPaintColor.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Cor da pintura (F.E)",
                options = PaintColor.entries.map { entry ->
                    val selected=externalPaintColor.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Decoração plástica",
                options = PlasticDecoration.entries.map { entry ->
                    val selected=plasticDecoration.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Outros atributos formais",
                options = AccessoryType.entries.map { entry ->
                    val selected=otherFormalAttributes.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Posição corporal",
                options = BodyPosition.entries.map { entry ->
                    val selected=bodyPosition.contains(entry)
                    OptionsPDFModel(entry.getNameTranslated(), selected)
                })
        )
        results.add(
            PDFFormStructureModel(
                TypeFormPDF.SELECT,
                "Usos",
                options = Uses.entries.map { entry ->
                    val selected=uses.contains(entry)
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

// Enum classes to capture the fixed options

enum class StatueType { ANTHROPOMORPHIC, ZOOMORPHIC, ANTHROPOZOOMORPHIC, HEAD, BODY, OTHER }
enum class Condition { INTACT, FRAGMENTED, RECONSTITUTED }
enum class GeneralBodyShape { SOLID, HOLLOW, NATURALISTIC, STYLIZED, CYLINDRICAL, GLOBULAR, TRAPEZOIDAL, PHALLIC }

enum class UpperLimbs { HEAD, EYES, NOSE, EARS, MOUTH, NECK, SHOULDERS, CHEST, BREASTS, ARMS, HANDS, FINGERS, NAVEL, }
enum class LowerLimbs { BUTTOCKS, ANUS, LEGS, FEET, TOES }
enum class Genitalia { MALE, FEMALE }
enum class Firing { OXIDIZING, REDUCING, REDUCED_CORE, INTERNAL_OXIDIZING_EXTERNAL_REDUCING, INTERNAL_REDUCING_EXTERNAL_OXIDIZING, UNIDENTIFIED }
enum class Temper { MINERAL, QUARTZ, MICA, IRON_OXIDE, CHARCOAL, GROG, CARAIPE, SHELL, CAUIXI }
enum class ManufacturingTechnique { COIL_BUILDING, MODELING, SLAB_BUILDING, PIT_FINGER, MOLDING }
enum class ManufacturingMarks { IMPRESSIONS, FIRING_MARKS }
enum class UsageMarks { CARBON_CRUST, SOOT, RESIN, SCRATCHES, NONE, OTHER }
enum class SurfaceTreatment { SMOOTHED, POLISHED, BURNISHED, RESIN, BRUSHED, SLIP, BARBOTINE, NONE }
enum class DecorationLocation { EXTERNAL, INTERNAL, BOTH }
enum class DecorationType { PAINTED, PLASTIC }
enum class PaintColor { WHITE, BLACK, CREAM_BEIGE, OTHER, UNIDENTIFIED }
enum class PlasticDecoration { INCISION, EXCISION, EYE_APPLIQUE, MAMIFORM_APPLIQUE, PUNCTATION, FINGERPRINT, PERFORATED_HOLE, NON_PERFORATED_HOLE, HANDLE}
enum class AccessoryType { STRETCHED_LOBES, THONGS, CLOTHING, ORNAMENTS, DIADEMS, HAIRSTYLE, MASK, EMBLEMS }
enum class BodyPosition { STANDING_FRONTAL, SQUATTING, STANDING_PROFILE, DUAL_PERSPECTIVE,ARMS_AWAY_FROM_THE_BODY,SEATED,ARMS_CLOSE_TO_THE_BODY }
enum class Uses { RATTLE, AMULET, CONTAINER, PIPE, MUSICAL_INSTRUMENT, WEAPON, OTHER, NOT_IDENTIFIED }