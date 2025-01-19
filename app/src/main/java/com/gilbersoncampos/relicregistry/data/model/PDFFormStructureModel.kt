package com.gilbersoncampos.relicregistry.data.model

import android.graphics.Bitmap
import com.gilbersoncampos.relicregistry.data.enums.TypeFormPDF
import org.w3c.dom.Text

data class PDFFormStructureModel(val type:TypeFormPDF, val title:String, val options:List<OptionsPDFModel>?=null,val text:String?=null, val images:List<Bitmap>?=null)
data class OptionsPDFModel(val label:String, val selected:Boolean)
