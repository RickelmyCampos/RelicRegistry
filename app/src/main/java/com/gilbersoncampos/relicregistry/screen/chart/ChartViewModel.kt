package com.gilbersoncampos.relicregistry.screen.chart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.enums.FilterEnum
import com.gilbersoncampos.relicregistry.data.repository.DataAnalysisRepository
import com.gilbersoncampos.relicregistry.data.useCase.DataAnalysisUseCase
import com.github.tehras.charts.bar.BarChartData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KClass

@HiltViewModel
class ChartViewModel @Inject constructor(private val dataAnalysisUseCase: DataAnalysisUseCase) :
    ViewModel() {
    private var _barChartData = MutableStateFlow<List<BarChartData.Bar>>(listOf())
    val barChartData: StateFlow<List<BarChartData.Bar>> = _barChartData.asStateFlow()
    private var _selectedFilter = MutableStateFlow<FilterEnum?>(null)
    var selectedFilter: StateFlow<FilterEnum?> = _selectedFilter.asStateFlow()

    private fun applyFilter() {
        viewModelScope.launch {
            _selectedFilter.value?.let {
                dataAnalysisUseCase(it).collect { data ->
                    _barChartData.value = data
                }
            }
        }
    }

    fun setFilterSelected(filterEnum: FilterEnum) {
        _selectedFilter.value = filterEnum
        applyFilter()
    }

}