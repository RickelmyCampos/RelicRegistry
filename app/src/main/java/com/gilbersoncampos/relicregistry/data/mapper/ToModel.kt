package com.gilbersoncampos.relicregistry.data.mapper

import com.gilbersoncampos.relicregistry.data.local.entity.RecordEntity
import com.gilbersoncampos.relicregistry.data.model.RecordModel

fun RecordEntity.toModel(): RecordModel = RecordModel(
    id = id,
    listImages=listImages,
    numbering = numbering,
    place = place,
    shelf = shelf,
    box = box,
    group = group,
    length = length,
    width = width,
    height = height,
    weight = weight,
    formType = formType.ifEmpty { null },
    formCondition = formCondition.ifEmpty { null },
    formGeneralBodyShape = formGeneralBodyShape.ifEmpty { null },
    upperLimbs = upperLimbs,
    lowerLimbs = lowerLimbs,
    genitalia = genitalia.ifEmpty { null },
    bodyPosition = bodyPosition,
    otherFormalAttributes = otherFormalAttributes,
    uses = uses,
    surfaceTreatment = surfaceTreatment,
    surfaceTreatmentET = surfaceTreatmentET,
    usageMarks = usageMarks,
    fabricationMarks = fabricationMarks,
    fabricationTechnique = fabricationTechnique,
    antiplastic = antiplastic,
    burn = burn,
    decoration = decoration,
    decorationLocation = decorationLocation.ifEmpty { null },
    decorationType = decorationType,
    paintColorFI = paintColorFI,
    location = location.ifEmpty { null },
    paintColorFE = paintColorFE,
    plasticDecoration = plasticDecoration
)