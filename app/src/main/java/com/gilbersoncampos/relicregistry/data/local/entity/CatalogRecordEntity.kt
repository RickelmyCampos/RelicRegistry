package com.gilbersoncampos.relicregistry.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gilbersoncampos.relicregistry.data.local.database.Converters
import com.gilbersoncampos.relicregistry.data.enums.UpperLimbs

@Entity(tableName = "catalog_records")
@TypeConverters(Converters::class)
data class CatalogRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val listImages: List<String>,
    val archaeologicalSite: String,
    val identification: String,
    val classification: String,
    val shelfLocation: String,
    val group: String,

    // Typology
    val statueType: String?,
    val condition: String?,
    val generalBodyShape: String?,

    // Portions
    val upperLimbs: String,
    val lowerLimbs: String,    // Store as JSON string

    // Genitalia
    val genitalia: String?,

    // Dimensions
    val length: Float?,
    val width: Float?,
    val height: Float?,
    val weight: Float?,

    // Technology
    val firing: String,
    val temper: String, // Store as JSON string
    val manufacturingTechnique: String, // Store as JSON string
    val manufacturingMarks: String, // Store as JSON string
    val usageMarks: String, // Store as JSON string
    val surfaceTreatmentInternal: String,
    val surfaceTreatmentExternal: String,

    // Decoration
    val decorationLocation: String?,
    val decorationType: String,
    val internalPaintColor: String, // Store as JSON string
    val externalPaintColor: String, // Store as JSON string
    val plasticDecoration: String, // Store as JSON string

    // Other Formal Attributes
    val otherFormalAttributes: String, // Store as JSON string

    // Body Position
    val bodyPosition: String,

    // Uses
    val uses: String, // Store as JSON string

    // Observations
    val observations: String,
    val hasDecoration: Boolean ,
)
