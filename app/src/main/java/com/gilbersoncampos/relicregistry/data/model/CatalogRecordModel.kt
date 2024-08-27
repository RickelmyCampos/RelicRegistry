package com.gilbersoncampos.relicregistry.data.model

data class CatalogRecordModel(
    val id:Long,
    val listImages: List<String> = listOf(),
    val archaeologicalSite: String,
    val identification: String,
    val classification: String,
    val shelfLocation: String,
    val group: String,
    // Typology
    val statueType: StatueType?=null,
    val condition: Condition?=null,
    val generalBodyShape: GeneralBodyShape?=null,
    // Portions
    val upperLimbs: List<UpperLimbs> = listOf(),
    val lowerLimbs: List<LowerLimbs> = listOf(),
    // Genitalia
    val genitalia: Genitalia?=null,
    // Dimensions
    val length: Float?=null,
    val width: Float?=null,
    val height: Float?=null,
    val weight: Float?=null,
    // Technology
    val firing: List<Firing> = listOf(),
    val temper: List<Temper> = listOf(),
    val manufacturingTechnique: List<ManufacturingTechnique> = listOf(),
    val manufacturingMarks: List<ManufacturingMarks> = listOf(),
    val usageMarks: List<UsageMarks> = listOf(),
    val surfaceTreatmentInternal: List<SurfaceTreatment> = listOf(),
    val surfaceTreatmentExternal: List<SurfaceTreatment> = listOf(),
    // Decoration
    val decorationLocation: DecorationLocation?=null,
    val decorationType: List<DecorationType> = listOf(),
    val internalPaintColor: List<PaintColor> = listOf(),
    val externalPaintColor: List<PaintColor> = listOf(),
    val plasticDecoration: List<PlasticDecoration> = listOf(),
    // Other Formal Attributes
    val otherFormalAttributes: List<String> = listOf(),
    // Body Position
    val bodyPosition: List<BodyPosition> = listOf(),
    // Uses
    val uses: List<String> = listOf(),
    // Observations
    val observations: String,
    val hasDecoration: Boolean = false,
)

// Enum classes to capture the fixed options

enum class StatueType { ANTHROPOMORPHIC, ZOOMORPHIC, ANTHROPOZOOMORPHIC, HEAD, BODY, OTHER }
enum class Condition { INTACT, FRAGMENTED, RECONSTITUTED }
enum class GeneralBodyShape { CYLINDRICAL, GLOBULAR, PHALLIC }
enum class UpperLimbs { HEAD, EYES, NOSE, EARS, MOUTH, NECK, SHOULDERS, CHEST, BREASTS, ARMS, HANDS, FINGERS, NAVEL, LEGS, FEET, ANUS, BUTTOCKS }
enum class LowerLimbs { HEAD, EYES, NOSE, EARS, MOUTH, NECK, SHOULDERS, CHEST, BREASTS, ARMS, HANDS, FINGERS, NAVEL, LEGS, FEET, ANUS, BUTTOCKS }
enum class Genitalia { MALE, FEMALE }
enum class Firing { OXIDIZING, REDUCING, REDUCED_CORE, INTERNAL_OXIDIZING_EXTERNAL_REDUCING, INTERNAL_REDUCING_EXTERNAL_OXIDIZING, UNIDENTIFIED }
enum class Temper { MINERAL, QUARTZ, MICA, IRON_OXIDE, CHARCOAL, GROG, CARAIPE, SHELL, CAUIXI }
enum class ManufacturingTechnique { COIL_BUILDING, MODELING, SLAB_BUILDING, PIT_FINGER, MOLDING, NATURALISTIC, STYLIZED, TRAPEZOIDAL, HOLLOW, SOLID }
enum class ManufacturingMarks { IMPRESSIONS, FIRING_MARKS }
enum class UsageMarks { CARBON_CRUST, SOOT, RESIN, SCRATCHES, NONE, OTHER }
enum class SurfaceTreatment { SMOOTHED, POLISHED, BURNISHED, RESIN, BRUSHED, SLIP, BARBOTINE, NONE }
enum class DecorationLocation { EXTERNAL, INTERNAL, BOTH }
enum class DecorationType { PAINTED, PLASTIC }
enum class PaintColor { WHITE, BLACK, CREAM_BEIGE, OTHER, UNIDENTIFIED }
enum class PlasticDecoration { INCISION, EXCISION, EYE_APPLIQUE, MAMIFORM_APPLIQUE, PUNCTATION, FINGERPRINT, PERFORATED_HOLE, NON_PERFORATED_HOLE, HANDLE, ENLARGED_LOBES, LOINCLOTH, GARMENTS, ORNAMENTS, DIADEMS, HAIRSTYLE, MASK, EMBLEMS }
enum class BodyPosition { STANDING_FRONTAL, SQUATTING, STANDING_PROFILE, DUAL_PERSPECTIVE, RATTLE, AMULET, CONTAINER, PIPE, MUSICAL_INSTRUMENT, WEAPON, OTHER }
