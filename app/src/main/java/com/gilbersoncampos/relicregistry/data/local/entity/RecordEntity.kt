package com.gilbersoncampos.relicregistry.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val listImages: List<String>,
    @ColumnInfo val numbering: String,
    @ColumnInfo val place: String,
    @ColumnInfo val shelf: String,
    @ColumnInfo val box: String,
    @ColumnInfo val group: String,
    //Dimensoes
    @ColumnInfo val length: Float,
    @ColumnInfo val width: Float,
    @ColumnInfo val height: Float,
    @ColumnInfo val weight: Float,
    //Tipologia
    @ColumnInfo val formType: String,
    @ColumnInfo val formCondition: String,
    @ColumnInfo val formGeneralBodyShape: String,
    //Morfologia
    @ColumnInfo val upperLimbs: List<String>,
    @ColumnInfo val lowerLimbs: List<String>,
    @ColumnInfo val genitalia: String,
    @ColumnInfo val bodyPosition: List<String>,
    @ColumnInfo val otherFormalAttributes: List<String>,
    //Tecnologia
    @ColumnInfo val burn: List<String>,
    @ColumnInfo val antiplastic: List<String>,
    @ColumnInfo val fabricationTechnique: List<String>,
    @ColumnInfo val fabricationMarks: List<String>,
    @ColumnInfo val usageMarks: List<String>,
    @ColumnInfo val surfaceTreatment: List<String>,
    @ColumnInfo val surfaceTreatmentET: List<String>,
    //Decoração
    @ColumnInfo val decoration: Boolean,
    @ColumnInfo val decorationLocation: String,
    @ColumnInfo val decorationType: List<String>,
    @ColumnInfo val paintColorFI: List<String>,
    @ColumnInfo val paintColorFE: List<String>,
    @ColumnInfo val plasticDecoration: List<String>,
    @ColumnInfo val location: String,
    //Usos
    @ColumnInfo val uses: List<String>
)
