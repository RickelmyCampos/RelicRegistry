package com.gilbersoncampos.relicregistry.data.services

import android.graphics.Bitmap
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel

interface PdfService {
    fun generatePdf(record:CatalogRecordModel,listImages: List<Bitmap>)
}