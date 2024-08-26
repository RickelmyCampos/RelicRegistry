package com.gilbersoncampos.relicregistry.data.model

data class RecordModel(
    val id: Int,
    val numbering: String,
    val place: String,
    val shelf: String,
    val box: String,
    val group: String,
    //Dimensoes
    val length: Float = 0f,
    val width: Float = 0f,
    val height: Float = 0f,
    val weight: Float = 0f,
    //Tipologia
    val formType: String? = null,
    val formCondition: String? = null,
    val formGeneralBodyShape: String? = null,
    //Morfologia
    val upperLimbs: List<String> = emptyList(),
    val lowerLimbs: List<String> = emptyList(),
    val genitalia: String? = null,
    val bodyPosition: List<String> = emptyList(),
    val otherFormalAttributes: List<String> = emptyList(),
    //Tecnologia
    val burn: List<String> = emptyList(),
    val antiplastic: List<String> = emptyList(),
    val fabricationTechnique: List<String> = emptyList(),
    val fabricationMarks: List<String> = emptyList(),
    val usageMarks: List<String> = emptyList(),
    val surfaceTreatment: List<String> = emptyList(),
    val surfaceTreatmentET: List<String> = emptyList(),
    //Decoração
    val decoration: Boolean = false,
    val decorationLocation: String? = null,
    val decorationType: List<String> = emptyList(),
    val paintColorFI: List<String> = emptyList(),
    val paintColorFE: List<String> = emptyList(),
    val plasticDecoration: List<String> = emptyList(),
    val location: String? = null,
    //Usos
    val uses: List<String> = emptyList()
)