package com.gilbersoncampos.relicregistry.data.mapper

import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel

fun CatalogRecordModel.toRecordRemote(): HashMap<String,Any?> {
    return hashMapOf(
        "archaeologicalSite" to this.archaeologicalSite,
        "identification" to this.identification,
        "classification" to this.classification,
        "shelfLocation" to this.shelfLocation,
        "group" to this.group,

        "statueType" to this.statueType?.name,
        "condition" to this.condition?.name,
        "generalBodyShape" to this.generalBodyShape?.name,

        "upperLimbs" to this.upperLimbs,
        "lowerLimbs" to this.lowerLimbs,

        "genitalia" to this.genitalia?.name,
        "length" to this.length,
        "width" to this.width,
        "height" to this.height,
        "weight" to this.weight,

        "firing" to this.firing,
        "temper" to this.temper,
        "manufacturingTechnique" to this.manufacturingTechnique,
        "manufacturingMarks" to this.manufacturingMarks,
        "usageMarks" to this.usageMarks,
        "surfaceTreatmentInternal" to this.surfaceTreatmentInternal,
        "surfaceTreatmentExternal" to this.surfaceTreatmentExternal,

        "decorationLocation" to this.decorationLocation?.name,
        "decorationType" to this.decorationType,
        "internalPaintColor" to this.internalPaintColor,
        "externalPaintColor" to this.externalPaintColor,
        "plasticDecoration" to this.plasticDecoration,

        "otherFormalAttributes" to this.otherFormalAttributes,
        "bodyPosition" to this.bodyPosition,
        "uses" to this.uses,

        "observations" to this.observations,
        "listImages" to this.listImages,
        "id" to this.id,
        "hasDecoration" to this.hasDecoration,
        "createdAt" to this.createdAt.toString(),
    )

}