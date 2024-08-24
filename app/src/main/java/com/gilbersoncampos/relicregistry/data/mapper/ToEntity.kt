package com.gilbersoncampos.relicregistry.data.mapper

import com.gilbersoncampos.relicregistry.data.local.entity.RecordEntity
import com.gilbersoncampos.relicregistry.data.model.RecordModel

fun RecordModel.toEntity(): RecordEntity = RecordEntity(id, numbering, place, shelf, box, group)