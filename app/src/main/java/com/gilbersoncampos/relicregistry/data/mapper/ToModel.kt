package com.gilbersoncampos.relicregistry.data.mapper

import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
import com.gilbersoncampos.relicregistry.data.enums.AccessoryType
import com.gilbersoncampos.relicregistry.data.enums.BodyPosition
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.enums.Condition
import com.gilbersoncampos.relicregistry.data.enums.DecorationLocation
import com.gilbersoncampos.relicregistry.data.enums.DecorationType
import com.gilbersoncampos.relicregistry.data.enums.Firing
import com.gilbersoncampos.relicregistry.data.enums.GeneralBodyShape
import com.gilbersoncampos.relicregistry.data.enums.Genitalia
import com.gilbersoncampos.relicregistry.data.enums.LowerLimbs
import com.gilbersoncampos.relicregistry.data.enums.ManufacturingMarks
import com.gilbersoncampos.relicregistry.data.enums.ManufacturingTechnique
import com.gilbersoncampos.relicregistry.data.enums.PaintColor
import com.gilbersoncampos.relicregistry.data.enums.PlasticDecoration
import com.gilbersoncampos.relicregistry.data.enums.StatueType
import com.gilbersoncampos.relicregistry.data.enums.SurfaceTreatment
import com.gilbersoncampos.relicregistry.data.enums.Temper
import com.gilbersoncampos.relicregistry.data.enums.UpperLimbs
import com.gilbersoncampos.relicregistry.data.enums.UsageMarks
import com.gilbersoncampos.relicregistry.data.enums.Uses
import com.gilbersoncampos.relicregistry.data.local.entity.HistoricSyncEntity
import com.gilbersoncampos.relicregistry.data.model.HistoricSyncModel
import com.gilbersoncampos.relicregistry.data.model.StatusSync
import com.gilbersoncampos.relicregistry.extensions.toLocalDateTime
import com.google.firebase.firestore.DocumentSnapshot


fun CatalogRecordEntity.toModel(): CatalogRecordModel {
    return CatalogRecordModel(
        archaeologicalSite = this.archaeologicalSite,
        identification = this.identification,
        classification = this.classification,
        shelfLocation = this.shelfLocation,
        group = this.group,

        // Typology
        statueType = this.statueType?.let { StatueType.valueOf(it) },
        condition = this.condition?.let { Condition.valueOf(it) },
        generalBodyShape = this.generalBodyShape?.let { GeneralBodyShape.valueOf(it) },

        // Portions
        upperLimbs = this.upperLimbs.takeIf { it.isNotEmpty() }
            ?.split(",")
            ?.map { UpperLimbs.valueOf(it.trim()) }
            ?: listOf(),
        lowerLimbs = this.lowerLimbs.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { LowerLimbs.valueOf(it.trim()) } ?: listOf(),

        // Genitalia
        genitalia = this.genitalia?.let { Genitalia.valueOf(it) },

        // Dimensions
        length = this.length,
        width = this.width,
        height = this.height,
        weight = this.weight,

        // Technology
        firing = this.firing.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { Firing.valueOf(it.trim()) } ?: listOf(),
        temper = this.temper.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { Temper.valueOf(it.trim()) } ?: listOf(),
        manufacturingTechnique = this.manufacturingTechnique.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { ManufacturingTechnique.valueOf(it.trim()) } ?: listOf(),
        manufacturingMarks = this.manufacturingMarks.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { ManufacturingMarks.valueOf(it.trim()) } ?: listOf(),
        usageMarks = this.usageMarks.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { UsageMarks.valueOf(it.trim()) } ?: listOf(),
        surfaceTreatmentInternal = this.surfaceTreatmentInternal.takeIf { it.isNotEmpty() }
            ?.split(",")
            ?.map { SurfaceTreatment.valueOf(it.trim()) } ?: listOf(),
        surfaceTreatmentExternal = this.surfaceTreatmentExternal.takeIf { it.isNotEmpty() }
            ?.split(",")
            ?.map { SurfaceTreatment.valueOf(it.trim()) } ?: listOf(),

        // Decoration
        decorationLocation = this.decorationLocation?.let { DecorationLocation.valueOf(it) },
        decorationType = this.decorationType.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { DecorationType.valueOf(it.trim()) } ?: listOf(),
        internalPaintColor = this.internalPaintColor.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { PaintColor.valueOf(it.trim()) } ?: listOf(),
        externalPaintColor = this.externalPaintColor.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { PaintColor.valueOf(it.trim()) } ?: listOf(),
        plasticDecoration = this.plasticDecoration.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { PlasticDecoration.valueOf(it.trim()) } ?: listOf(),

        // Other Formal Attributes
        otherFormalAttributes = this.otherFormalAttributes.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { AccessoryType.valueOf(it.trim()) } ?: listOf(),

        // Body Position
        bodyPosition = this.bodyPosition.takeIf { it.isNotEmpty() }?.split(",")
            ?.map { BodyPosition.valueOf(it.trim()) } ?: listOf(),

        // Uses
        uses = this.uses.takeIf { it.isNotEmpty() }?.split(",")?.map { Uses.valueOf(it.trim()) }
            ?: listOf(),

        // Observations
        observations = this.observations,
        id = this.id,
        listImages = this.listImages,
        hasDecoration = this.hasDecoration,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        idRemote = this.idRemote

    )
}
fun HistoricSyncEntity.toModel():HistoricSyncModel{
    return HistoricSyncModel(
        id=this.id,
        status=StatusSync.valueOf(this.status),
        data = this.data,
        errorMessage = this.errorMessage,
        startIn = this.startIn,
        endIn = this.endIn)
}
fun DocumentSnapshot.toRecordModel():CatalogRecordModel{
    return CatalogRecordModel(
        archaeologicalSite = data!!["archaeologicalSite"].toString(),
        identification = data!!["identification"].toString(),
        classification = data!!["classification"].toString(),
        shelfLocation = data!!["shelfLocation"].toString(),
        group = data!!["group"].toString(),

        // Typology
        statueType = this.getEnumOrNull<StatueType>("statueType"),//this.getEnumOrNull<StatueType>("statueType"),
        condition =this.getEnumOrNull<Condition>("condition"),
        generalBodyShape = this.getEnumOrNull<GeneralBodyShape>("generalBodyShape"),

        // Portions
        upperLimbs = this.getEnumListOrNull("upperLimbs"),
        lowerLimbs = this.getEnumListOrNull("lowerLimbs"),

        // Genitalia
        genitalia = this.getEnumOrNull<Genitalia>("genitalia"),

        // Dimensions
        length = data?.get("length")?.toString()?.toFloat(),
        width = data?.get("width")?.toString()?.toFloat(),
        height = data?.get("height")?.toString()?.toFloat(),
        weight = data?.get("weight")?.toString()?.toFloat(),

        // Technology
        firing = this.getEnumListOrNull("firing"),
        temper = this.getEnumListOrNull("temper"),
        manufacturingTechnique =this.getEnumListOrNull("manufacturingTechnique"),
        manufacturingMarks = this.getEnumListOrNull("manufacturingMarks"),
        usageMarks = this.getEnumListOrNull("usageMarks"),
        surfaceTreatmentInternal = this.getEnumListOrNull("surfaceTreatmentInternal"),
        surfaceTreatmentExternal = this.getEnumListOrNull("surfaceTreatmentExternal"),

        // Decoration
        decorationLocation = this.getEnumOrNull<DecorationLocation>("decorationLocation"),
        decorationType = this.getEnumListOrNull("decorationType"),
        internalPaintColor = this.getEnumListOrNull("internalPaintColor"),
        externalPaintColor = this.getEnumListOrNull("externalPaintColor"),
        plasticDecoration = this.getEnumListOrNull("plasticDecoration"),

        // Other Formal Attributes
        otherFormalAttributes = this.getEnumListOrNull("otherFormalAttributes"),

        // Body Position
        bodyPosition = this.getEnumListOrNull("bodyPosition"),

        // Uses
        uses = this.getEnumListOrNull("uses"),

        // Observations
        observations = data!!["observations"].toString(),
        id = 0,
        listImages = data?.get("listImages") as? List<String> ?: listOf(),
        hasDecoration = data!!["hasDecoration"].toString().toBoolean(),
        createdAt = data!!["createdAt"].toString().toLocalDateTime(),
        updatedAt = data!!["updatedAt"].toString().toLocalDateTime(),
        idRemote = this.id
    )
}
inline fun <reified T : Enum<T>> DocumentSnapshot.getEnumListOrNull(key: String): List<T> {
    val rawList = this.get(key) as? List<*>
    return rawList?.mapNotNull { item ->
        item?.toString()?.trim()?.let { str ->
            enumValues<T>().firstOrNull { it.name.equals(str, ignoreCase = true) }
        }
    }?: listOf()
}
inline  fun <reified T: Enum<T>>  DocumentSnapshot.getEnumOrNull(key: String): T? {
    val key =this.get(key) as? String
   return key?.let { strEnum->
       strEnum.toString().trim().let { str ->
           enumValues<T>().firstOrNull { it.name.equals(str, ignoreCase = true) }
       }
   }
}