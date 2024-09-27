package com.gilbersoncampos.relicregistry.data.useCase

import android.util.Log
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import javax.inject.Inject

class DeleteCacheUseCase @Inject constructor(private val imageStoreService: ImageStoreService) {
    operator fun invoke() {
        imageStoreService.listAllImageCached().forEach { imageStoreService.deleteCache(it) }
    }
}