package com.gilbersoncampos.relicregistry.data.model

import com.gilbersoncampos.relicregistry.data.enums.FormFieldType

data class FormField(val id:String, val label:String, val typeField: FormFieldType, val value:String,val options:String?=null)

