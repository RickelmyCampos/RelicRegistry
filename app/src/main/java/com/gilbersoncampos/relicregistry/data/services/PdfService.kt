package com.gilbersoncampos.relicregistry.data.services

import android.graphics.Bitmap
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import java.io.File

interface PdfService {
    fun generatePdf(record:CatalogRecordModel,listImages: List<Bitmap>)
    fun getPDF(): File
}