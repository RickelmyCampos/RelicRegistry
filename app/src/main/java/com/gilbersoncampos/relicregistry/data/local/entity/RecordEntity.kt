package com.gilbersoncampos.relicregistry.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val numbering: String,
    @ColumnInfo val place: String,
    @ColumnInfo val shelf: String,
    @ColumnInfo val box: String,
    @ColumnInfo val group: String
)
