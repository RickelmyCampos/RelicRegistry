package com.gilbersoncampos.relicregistry.screen.config

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import com.gilbersoncampos.relicregistry.data.useCase.DeleteCacheUseCase
import com.gilbersoncampos.relicregistry.screen.historic.navigateToHistoric
import com.gilbersoncampos.relicregistry.screen.recordList.RecordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val deleteCacheUseCase: DeleteCacheUseCase) :
    ViewModel() {
    private var _settingOptions = MutableStateFlow<List<SettingModel>>(
        mutableListOf(SettingModel("Limpar cache", ActionToClick.OnClearCache),
        SettingModel("Histórico de sync",ActionToClick.OnNavigateHistoric))
    )
    val settingOptions: StateFlow<List<SettingModel>> = _settingOptions.asStateFlow()
//    val settingOptions = listOf(SettingModel("Limpar cache", ActionToClick.OnClearCache),
//        SettingModel("Histórico de sync",ActionToClick.OnNavigateHistoric))
    fun addOption(settingModel: SettingModel) {
        _settingOptions.value = _settingOptions.value.plus(settingModel)
    }
    fun onClickOption(action: ActionToClick,navHostController: NavHostController?) {
        when (action) {
            ActionToClick.OnClearCache -> {
                deleteCacheUseCase()
            }
           is ActionToClick.Default -> {
                action.click()
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
    data class Default(val click:()->Unit) : ActionToClick
}