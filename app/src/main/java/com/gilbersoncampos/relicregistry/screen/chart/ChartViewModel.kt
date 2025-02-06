package com.gilbersoncampos.relicregistry.screen.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gilbersoncampos.relicregistry.data.enums.FilterEnum
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import com.gilbersoncampos.relicregistry.data.useCase.DataAnalysisUseCase
import com.github.tehras.charts.bar.BarChartData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val dataAnalysisUseCase: DataAnalysisUseCase
) :
    ViewModel() {
    private var _barChartData = MutableStateFlow<List<BarChartData.Bar>>(listOf())
    val barChartData: StateFlow<List<BarChartData.Bar>> = _barChartData.asStateFlow()
    private var _siteList = MutableStateFlow<List<String?>>(listOf(null))
    val siteList: StateFlow<List<String?>> = _siteList.asStateFlow()
    private var _selectedFilter = MutableStateFlow<FilterEnum?>(null)
    var selectedFilter: StateFlow<FilterEnum?> = _selectedFilter.asStateFlow()
    private var _siteSelected = MutableStateFlow<String?>(null)
    var siteSelected: StateFlow<String?> = _siteSelected.asStateFlow()

    init {
        viewModelScope.launch {
            recordRepository.getAllArchaeologicalSite().collectLatest {
                val listAux = mutableListOf<String?>(null)
                listAux.addAll(it)
                _siteList.value = listAux
            }

        }
    }

    private fun applyFilter() {
        viewModelScope.launch {
            _selectedFilter.value?.let {
                dataAnalysisUseCase(_siteSelected.value, it).collect { data ->
                    _barChartData.value = data
                }
            }
        }
    }

    fun setFilterSelected(filterEnum: FilterEnum) {
        _selectedFilter.value = filterEnum
        applyFilter()
    }

    fun setSiteSelected(site: String?) {
        _siteSelected.value = site
        applyFilter()
    }

}