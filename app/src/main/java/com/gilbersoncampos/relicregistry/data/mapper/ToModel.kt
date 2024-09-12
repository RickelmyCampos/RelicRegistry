package com.gilbersoncampos.relicregistry.data.mapper

import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
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
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.model.StatueType
import com.gilbersoncampos.relicregistry.data.model.SurfaceTreatment
import com.gilbersoncampos.relicregistry.data.model.Temper
import com.gilbersoncampos.relicregistry.data.model.UpperLimbs
import com.gilbersoncampos.relicregistry.data.model.UsageMarks
import com.gilbersoncampos.relicregistry.data.model.Uses


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
        hasDecoration = this.hasDecoration
    )
}