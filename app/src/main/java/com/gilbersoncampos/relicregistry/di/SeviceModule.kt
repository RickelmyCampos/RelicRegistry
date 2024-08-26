package com.gilbersoncampos.relicregistry.di

import android.content.Context
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import com.gilbersoncampos.relicregistry.service.ExternalPrivateImageStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SeviceModule {
    @Provides
    fun providesImageStoreService(@ApplicationContext applicationContext: Context): ImageStoreService =
        ExternalPrivateImageStoreService(applicationContext)

}