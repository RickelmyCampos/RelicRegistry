package com.gilbersoncampos.relicregistry.data.repository

import com.github.tehras.charts.bar.BarChartData
import kotlinx.coroutines.flow.Flow

interface DataAnalysisRepository {
    suspend fun filterData():Flow<List<BarChartData.Bar>>
}