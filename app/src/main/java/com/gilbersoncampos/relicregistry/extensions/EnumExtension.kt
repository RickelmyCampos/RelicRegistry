package com.gilbersoncampos.relicregistry.extensions

import com.gilbersoncampos.relicregistry.data.model.AccessoryType
import com.gilbersoncampos.relicregistry.data.model.BodyPosition
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

fun Enum<*>.getNameTranslated(): String {
    return when (this) {
        is PaintColor -> when (this) {
            PaintColor.BLACK -> "Preto"
            PaintColor.WHITE -> "Branco"
            PaintColor.CREAM_BEIGE -> "Creme/Bege"
            PaintColor.OTHER -> "Outra"
            PaintColor.UNIDENTIFIED -> "Não identificado"
        }

        is StatueType -> when (this) {
            StatueType.ANTHROPOMORPHIC -> "Antropomorfo"
            StatueType.ZOOMORPHIC -> "Zoomorfo"
            StatueType.ANTHROPOZOOMORPHIC -> "Antropozoomorfo"
            StatueType.HEAD -> "Cabeça de estatueta"
            StatueType.BODY -> "Corpo de estatueta"
            StatueType.OTHER -> "Não identificado"
        }

        is Condition -> when (this) {
            Condition.INTACT -> "Inteiro"
            Condition.FRAGMENTED -> "Fragmanetado"
            Condition.RECONSTITUTED -> "Reconstituido"
        }

        is GeneralBodyShape -> when (this) {
            GeneralBodyShape.SOLID -> "Sólida"
            GeneralBodyShape.HOLLOW -> "Oca"
            GeneralBodyShape.NATURALISTIC -> "Naturalista"
            GeneralBodyShape.STYLIZED -> "Estilizada"
            GeneralBodyShape.CYLINDRICAL -> "Cilíndrica"
            GeneralBodyShape.GLOBULAR -> "Globular"
            GeneralBodyShape.TRAPEZOIDAL -> "Trapezoidal"
            GeneralBodyShape.PHALLIC -> "Fálica"
        }

        is UpperLimbs -> when (this) {
            UpperLimbs.HEAD -> "Cabeça"
            UpperLimbs.EYES -> "Olhos"
            UpperLimbs.NOSE -> "Nariz"
            UpperLimbs.EARS -> "Orelhas"
            UpperLimbs.MOUTH -> "Boca"
            UpperLimbs.NECK -> "Pescoço"
            UpperLimbs.SHOULDERS -> "Ombros"
            UpperLimbs.CHEST -> "Tórax"
            UpperLimbs.BREASTS -> "Seios"
            UpperLimbs.ARMS -> "Braços"
            UpperLimbs.HANDS -> "Mãos"
            UpperLimbs.FINGERS ->  "Dedos"
            UpperLimbs.NAVEL -> "Umbigo"
        }

        is LowerLimbs -> when (this) {
            LowerLimbs.BUTTOCKS -> "Nadegas"
            LowerLimbs.ANUS ->  "Ânus"
            LowerLimbs.LEGS -> "Pernas"
            LowerLimbs.FEET -> "Pés"
            LowerLimbs.TOES -> "Dedos"
        }

        is Genitalia -> when (this) {
            Genitalia.MALE -> "Masculino"
            Genitalia.FEMALE -> "Feminino"
        }

        is Firing -> when (this) {
            Firing.OXIDIZING -> "Oxidante"
            Firing.REDUCING -> "Redutora"
            Firing.REDUCED_CORE ->"Núcleo Redutor"
            Firing.INTERNAL_OXIDIZING_EXTERNAL_REDUCING -> "Oxidação interna/externa"
            Firing.INTERNAL_REDUCING_EXTERNAL_OXIDIZING -> "Redução interna/externa"
            Firing.UNIDENTIFIED -> "Não Identificada"
        }

        is Temper -> when (this) {
            Temper.MINERAL -> "Mineral"
            Temper.QUARTZ -> "Quartzo"
            Temper.MICA ->  "Mica"
            Temper.IRON_OXIDE -> "Óxido de ferro"
            Temper.CHARCOAL ->"Carvão"
            Temper.GROG -> "Caco moído"
            Temper.CARAIPE -> "Caraipé"
            Temper.SHELL ->  "Concha"
            Temper.CAUIXI -> "Cauixi"
        }

        is ManufacturingTechnique -> when (this) {
            ManufacturingTechnique.COIL_BUILDING -> "Acordelado ou roleitado"
            ManufacturingTechnique.MODELING -> "Modelado"
            ManufacturingTechnique.SLAB_BUILDING -> "Placas"
            ManufacturingTechnique.PIT_FINGER -> "Moldado"
            ManufacturingTechnique.MOLDING -> "Pint-Finger"
        }

        is ManufacturingMarks -> when (this) {
            ManufacturingMarks.IMPRESSIONS -> "Impressão"
            ManufacturingMarks.FIRING_MARKS -> "Marca de queima"
        }

        is UsageMarks -> when (this) {
            UsageMarks.CARBON_CRUST -> "Crosta carbônica"
            UsageMarks.SOOT -> "Fuligem"
            UsageMarks.RESIN -> "Resina"
            UsageMarks.SCRATCHES -> "Ranhuras"
            UsageMarks.NONE -> "Não possui"
            UsageMarks.OTHER -> "Outra"
        }

        is SurfaceTreatment -> when (this) {
            SurfaceTreatment.SMOOTHED -> "Alisado"
            SurfaceTreatment.POLISHED -> "Polimento"
            SurfaceTreatment.BURNISHED ->  "Brunidura"
            SurfaceTreatment.RESIN -> "Resina"
            SurfaceTreatment.BRUSHED -> "Escovado"
            SurfaceTreatment.SLIP -> "Engobo"
            SurfaceTreatment.BARBOTINE -> "Barbotina"
            SurfaceTreatment.NONE -> "Não possui"
        }

        is DecorationLocation -> when (this) {
            DecorationLocation.EXTERNAL -> "Externa"
            DecorationLocation.INTERNAL ->  "Interna"
            DecorationLocation.BOTH ->  "Ambos"
        }

        is DecorationType -> when (this) {
            DecorationType.PAINTED -> "Pintura"
            DecorationType.PLASTIC ->  "Plástica"
            // "Grafismo"
        }

        is PlasticDecoration -> when (this) {
            PlasticDecoration.INCISION -> "Incisão"
            PlasticDecoration.EXCISION -> "Excisão"
            PlasticDecoration.EYE_APPLIQUE ->"Aplique de olhos"
            PlasticDecoration.MAMIFORM_APPLIQUE -> "Aplique mamiforme"
            PlasticDecoration.PUNCTATION -> "Ponteado"
            PlasticDecoration.FINGERPRINT -> "Digitado"
            PlasticDecoration.PERFORATED_HOLE ->  "Orifício vazado"
            PlasticDecoration.NON_PERFORATED_HOLE -> "Orifício não vazado"
            PlasticDecoration.HANDLE -> "Alça"
            PlasticDecoration.ENLARGED_LOBES -> "TODO()"
            PlasticDecoration.LOINCLOTH ->"TODO()"
            PlasticDecoration.GARMENTS -> "TODO()"
            PlasticDecoration.ORNAMENTS -> "TODO()"
            PlasticDecoration.DIADEMS -> "TODO()"
            PlasticDecoration.HAIRSTYLE -> "TODO()"
            PlasticDecoration.MASK -> "TODO()"
            PlasticDecoration.EMBLEMS -> "TODO()"
        }

        is AccessoryType -> when (this) {
            AccessoryType.STRETCHED_LOBES ->"Lóbulos alargados"
            AccessoryType.THONGS -> "Tangas"
            AccessoryType.CLOTHING ->  "Vestimentas"
            AccessoryType.ORNAMENTS -> "Adornos"
            AccessoryType.DIADEMS -> "Diademas"
            AccessoryType.HAIRSTYLE -> "Penteado"
            AccessoryType.MASK -> "Máscara"
            AccessoryType.EMBLEMS -> "Emblemas"
        }

        is BodyPosition -> when (this) {
            BodyPosition.STANDING_FRONTAL -> "Em pé (Frontal)"
            BodyPosition.SQUATTING -> "Acocorado"
            BodyPosition.STANDING_PROFILE -> "Em pé (Perfil)"
            BodyPosition.DUAL_PERSPECTIVE -> "Perspectiva dual"
            BodyPosition.RATTLE -> "TODO()"
            BodyPosition.AMULET -> "TODO()"
            BodyPosition.CONTAINER -> "TODO()"
            BodyPosition.PIPE -> "TODO()"
            BodyPosition.MUSICAL_INSTRUMENT ->" TODO()"
            BodyPosition.WEAPON -> "TODO()"
            BodyPosition.OTHER -> "TODO()"
        }

        is Uses -> when (this) {
            Uses.RATTLE -> "Chocalho"
            Uses.AMULET -> "TODO()"
            Uses.CONTAINER -> "Recipiente"
            Uses.PIPE -> "TODO()"
            Uses.INSTRUMENT -> "Instrumento musical"
            Uses.MUSICAL -> "Instrumento musical"
            Uses.WEAPON -> "Arma"
            Uses.OTHER -> "Outra"
            Uses.NOT_IDENTIFIED -> "Não identificada"
        }

        else -> "Unknown name"
    }
}
