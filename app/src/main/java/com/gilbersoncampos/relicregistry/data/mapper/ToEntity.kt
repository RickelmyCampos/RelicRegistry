package com.gilbersoncampos.relicregistry.data.mapper

import com.gilbersoncampos.relicregistry.data.local.entity.RecordEntity
import com.gilbersoncampos.relicregistry.data.model.RecordModel

fun RecordModel.toEntity(): RecordEntity = RecordEntity(
    id = id,
    numbering = numbering,
    place = place,
    shelf = shelf,
    box = box,
    group = group,
    length = length,
    width = width,
    height = height,
    weight = weight,
    formType = formType?:"",
    formCondition = formCondition?:"",
    formGeneralBodyShape = formGeneralBodyShape?:"",
    upperLimbs = upperLimbs,
    lowerLimbs = lowerLimbs,
    genitalia = genitalia?:"",
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
    decorationLocation = decorationLocation?:"",
    decorationType = decorationType,
    paintColorFI = paintColorFI,
    location = location?:"",
    paintColorFE = paintColorFE,
    plasticDecoration = plasticDecoration
)