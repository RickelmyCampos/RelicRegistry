package com.gilbersoncampos.relicregistry.data.repository.Impl

import androidx.compose.ui.graphics.Color
import com.gilbersoncampos.relicregistry.data.local.dao.RecordDao
import com.gilbersoncampos.relicregistry.data.local.entity.FilterDataChart
import com.gilbersoncampos.relicregistry.data.mapper.toModel
import com.gilbersoncampos.relicregistry.data.repository.DataAnalysisRepository
import com.github.tehras.charts.bar.BarChartData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataAnalysisRepositoryImpl @Inject constructor( private val dao:RecordDao):DataAnalysisRepository {
    override suspend fun filterData():Flow<List<BarChartData.Bar> >{
        return flow{
            dao.fetchFilter("genitalia").map { list->list.map {  it.toModel() } }.collect{emit(it)}
        }
    }
}
fun FilterDataChart.toModel()=BarChartData.Bar(value = this.value.toFloat(), label = this.label?:"Não possui", color = Color.Red)