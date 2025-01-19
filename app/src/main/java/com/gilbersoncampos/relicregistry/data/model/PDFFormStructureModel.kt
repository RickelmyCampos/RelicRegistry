package com.gilbersoncampos.relicregistry.data.model

import com.gilbersoncampos.relicregistry.data.enums.TypeFormPDF

data class PDFFormStructureModel(val type:TypeFormPDF, val title:String, val options:List<OptionsPDFModel>?=null,val text:String?=null)
data class OptionsPDFModel(val label:String, val selected:Boolean)
