package com.gilbersoncampos.relicregistry.screen.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val imageStoreService: ImageStoreService) :ViewModel() {

    private lateinit var _path:String
    fun saveImage(bitmap: Bitmap) {
//      _path =  imageStoreService.saveImage(bitmap,"newImage").split("/").last()
//        Log.d("path",_path.split("/").last())
    }
    fun getImage() :Bitmap{
        return imageStoreService.getImage(_path,false)
    }

}