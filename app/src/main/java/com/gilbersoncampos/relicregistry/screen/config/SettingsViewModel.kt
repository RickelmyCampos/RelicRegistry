package com.gilbersoncampos.relicregistry.screen.config

import androidx.lifecycle.ViewModel
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import com.gilbersoncampos.relicregistry.data.useCase.DeleteCacheUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val deleteCacheUseCase: DeleteCacheUseCase) :
    ViewModel() {
    val settingOptions = listOf(SettingModel("Limpar cache", ActionToClick.OnClearCache))
    fun onClickOption(action: ActionToClick) {
        when (action) {
            ActionToClick.OnClearCache -> {
                deleteCacheUseCase()
            }

            else -> {}
        }
    }

    companion object {
        //val settingOptions = listOf(SettingModel("Limpar cache"))
    }
}

data class SettingModel(val name: String, val action: ActionToClick)
sealed interface ActionToClick {
    data object OnClearCache : ActionToClick
    data object Default : ActionToClick
}