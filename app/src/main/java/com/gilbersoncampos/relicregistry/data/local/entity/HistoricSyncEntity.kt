package com.gilbersoncampos.relicregistry.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gilbersoncampos.relicregistry.data.local.database.Converters
import java.time.LocalDateTime


@Entity(tableName = "historic_sync")
@TypeConverters(Converters::class)
data class HistoricSyncEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val status: String,
    val data: String,
    val errorMessage: String?=null,
    val startIn: LocalDateTime,
    val endIn: LocalDateTime?,
)
