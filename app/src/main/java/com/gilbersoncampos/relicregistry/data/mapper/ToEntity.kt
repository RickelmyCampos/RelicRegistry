package com.gilbersoncampos.relicregistry.data.mapper

import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.RecordModel



fun CatalogRecordModel.toEntity(): CatalogRecordEntity {
    return CatalogRecordEntity(
        archaeologicalSite = this.archaeologicalSite,
        identification = this.identification,
        classification = this.classification,
        shelfLocation = this.shelfLocation,
        group = this.group,

        // Typology
        statueType = this.statueType?.name,
        condition = this.condition?.name,
        generalBodyShape = this.generalBodyShape?.name,

        // Portions
        upperLimbs = this.upperLimbs.joinToString(","),
        lowerLimbs = this.lowerLimbs.joinToString(","),

        // Genitalia
        genitalia = this.genitalia?.name,

        // Dimensions
        length = this.length,
        width = this.width,
        height = this.height,
        weight = this.weight,

        // Technology
        firing = this.firing.joinToString(","),
        temper = this.temper.joinToString(","),
        manufacturingTechnique = this.manufacturingTechnique.joinToString(","),
        manufacturingMarks = this.manufacturingMarks.joinToString(","),
        usageMarks = this.usageMarks.joinToString(","),
        surfaceTreatmentInternal = this.surfaceTreatmentInternal.joinToString(","),
        surfaceTreatmentExternal = this.surfaceTreatmentExternal.joinToString(","),

        // Decoration
        decorationLocation = this.decorationLocation?.name,
        decorationType = this.decorationType.joinToString(","),
        internalPaintColor = this.internalPaintColor.joinToString(","),
        externalPaintColor = this.externalPaintColor.joinToString(","),
        plasticDecoration = this.plasticDecoration.joinToString(","),

        // Other Formal Attributes
        otherFormalAttributes = this.otherFormalAttributes.joinToString(","),

        // Body Position
        bodyPosition = this.bodyPosition.joinToString(","),

        // Uses
        uses = this.uses.joinToString(","),

        // Observations
        observations = this.observations,
        listImages = this.listImages,
        id = this.id
    )
}