package com.gilbersoncampos.relicregistry.screen.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.gilbersoncampos.relicregistry.data.enums.FilterEnum
import com.gilbersoncampos.relicregistry.ui.components.CustomDropdown
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData

@Composable
fun ChartsScreen(viewModel: ChartViewModel= hiltViewModel()) {
    Column {
        val list by viewModel.barChartData.collectAsState()
        val filterSelected by viewModel.selectedFilter.collectAsState()
        Text("Escolha o sitio")
        CustomDropdown(
            title = "Tipo",
            list = FilterEnum.entries,
            selectedState = filterSelected,
            onSelect = {
               viewModel.setFilterSelected(it)
            })
        Text("Chart")
        val barChartData = BarChartData(
            bars = list
        )
        BarChart(barChartData = barChartData)
    }
}