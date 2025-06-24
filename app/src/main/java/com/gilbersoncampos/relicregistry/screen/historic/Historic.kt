package com.gilbersoncampos.relicregistry.screen.historic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gilbersoncampos.relicregistry.R
import com.gilbersoncampos.relicregistry.data.model.HistoricSyncModel
import com.gilbersoncampos.relicregistry.data.model.StatusSync
import com.gilbersoncampos.relicregistry.extensions.toBrDateTime
import java.time.LocalDateTime

@Composable
fun HistoricScreen(viewModel: HistoricViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value

    when (uiState) {
        HistoricUiState.Error -> {
            Text(text = "Erro ao buscar a lista")
        }

        HistoricUiState.Loading -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
       is HistoricUiState.Success ->{
            HistoricUI(uiState.historic)
        }

    }
}
@Composable
fun HistoricUI(list: List<HistoricSyncModel>){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) { historic ->
            HistoricItem(historic)
        }
    }

}
@Composable
fun HistoricItem(item: HistoricSyncModel){
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 2.dp)) {
        Row (modifier = Modifier.fillMaxWidth(),Arrangement.SpaceBetween,Alignment.CenterVertically){
            Column {
                Text("Início: ${item.startIn.toBrDateTime()}")
                item.endIn?.let {
                    Text("Finalizou: ${it.toBrDateTime()}")
                }
            }
            when(item.status){
                StatusSync.SUCCESS -> {
                    Icon(painter = painterResource(R.drawable.ic_check_outline), contentDescription = "success", tint = Color.Green)
                }
                StatusSync.ERROR -> {
                    Icon(painter = painterResource(R.drawable.ic_error), contentDescription = "error",tint = Color.Red)
                }
                else -> {}
            }
        }
        if(item.status == StatusSync.LOADING){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        HorizontalDivider(thickness = 2.dp)
        //Text("Status: ${item.status.nameString}")
    }
}
@Composable
@Preview
fun HistoricItemPreview(){
    val item = HistoricSyncModel(id = 0, endIn = LocalDateTime.now(), status = StatusSync.SUCCESS, startIn = LocalDateTime.now(), data = "")
    Column {

    HistoricItem(item)
    HistoricItem(item.copy(status = StatusSync.LOADING, endIn = null))
    HistoricItem(item.copy(status = StatusSync.ERROR))
    HistoricItem(item)
    }
}