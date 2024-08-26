package com.gilbersoncampos.relicregistry.data.services

import android.graphics.Bitmap
import android.net.Uri

interface ImageStoreService {
    fun saveImage(bitmap: Bitmap,nameImage:String): String
    fun saveImageByUri(uri: Uri,nameImage:String):String
    fun getImage(nameImage: String): Bitmap

}