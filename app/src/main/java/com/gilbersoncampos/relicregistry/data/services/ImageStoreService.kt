package com.gilbersoncampos.relicregistry.data.services

import android.graphics.Bitmap
import android.net.Uri

interface ImageStoreService {
    fun saveImageByUri(uri: Uri,nameImage:String):String
    fun deleteImageByNameImage(nameImage:String)
    fun getImage(nameImage: String): Bitmap
    fun deleteUnsavedImages()
    fun renameImage(oldName:String,newName: String)

    fun saveCacheImage(imageName:String):String

}