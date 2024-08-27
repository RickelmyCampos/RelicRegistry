package com.gilbersoncampos.relicregistry.data.model

data class CheckboxOption(val name: String, var isChecked: Boolean=false)
data class FormModel( val id: Int,
                      val listImages: List<String> =emptyList(),
                      val numbering: String,
                      val place: String,
                      val shelf: String,
                      val box: String,
                      val group: String,
    //Dimensoes
                      val length: Float = 0f,
                      val width: Float = 0f,
                      val height: Float = 0f,
                      val weight: Float = 0f,)