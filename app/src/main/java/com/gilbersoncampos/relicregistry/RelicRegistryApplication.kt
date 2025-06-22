package com.gilbersoncampos.relicregistry

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration

@HiltAndroidApp
class RelicRegistryApplication: Application(), Configuration.Provider{
    override val workManagerConfiguration: Configuration
        get() =  Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

}