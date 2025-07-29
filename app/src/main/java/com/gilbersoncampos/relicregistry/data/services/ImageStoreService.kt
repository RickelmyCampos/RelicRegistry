package com.gilbersoncampos.relicregistry.data.services

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface ImageStoreService {
    fun getImage(imageName: String,isCache:Boolean): Bitmap
    fun getImageFile(imageName: String,isCache:Boolean): File
    fun saveCache(bitmap: Bitmap, imageName: String): String
    fun saveUriCache(uri: Uri, imageName: String): String
    fun convertUriToBitmap(uri: Uri): Bitmap
    fun copyToExternalStoreFromCache(imageName: String)
    fun copyToCacheFromExternalStore(imageName: String)
    fun deleteCache(imageName: String)
    fun listAllImageCached():List<String>
}