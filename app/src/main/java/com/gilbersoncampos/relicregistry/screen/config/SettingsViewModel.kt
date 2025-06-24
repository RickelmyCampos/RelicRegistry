package com.gilbersoncampos.relicregistry.screen.config

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import com.gilbersoncampos.relicregistry.data.useCase.DeleteCacheUseCase
import com.gilbersoncampos.relicregistry.screen.historic.navigateToHistoric
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val deleteCacheUseCase: DeleteCacheUseCase) :
    ViewModel() {
    val settingOptions = listOf(SettingModel("Limpar cache", ActionToClick.OnClearCache),
        SettingModel("Histórico de sync",ActionToClick.OnNavigateHistoric))
    fun onClickOption(action: ActionToClick,navHostController: NavHostController?) {
        when (action) {
            ActionToClick.OnClearCache -> {
                deleteCacheUseCase()
            }
            ActionToClick.Default -> {

            }
            ActionToClick.OnNavigateHistoric ->{
                navHostController?.navigateToHistoric()
            }
        }
    }

    companion object {
        //val settingOptions = listOf(SettingModel("Limpar cache"))
    }
}

data class SettingModel(val name: String, val action: ActionToClick)
sealed interface ActionToClick {
    data object OnClearCache : ActionToClick
    data object OnNavigateHistoric : ActionToClick
    data object Default : ActionToClick
}