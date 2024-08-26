package com.gilbersoncampos.relicregistry.screen.recordList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.gilbersoncampos.relicregistry.R
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme

@Composable
fun RecordListScreen(
    viewModel: RecordListViewModel = hiltViewModel(),
    navigateToEditRecord: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    RecordListUI(uiState, onSelectRecord = navigateToEditRecord)
}

@Composable
fun RecordListUI(uiState: RecordUiState, onSelectRecord: (Int) -> Unit) {


    Column(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            RecordUiState.Error -> {
                Text(text = "Erro ao buscar a lista")
            }

            RecordUiState.Loading -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is RecordUiState.Success -> {
                if (uiState.records.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Nenhuma ficha encontrada, Adicione clicando no botão abaixo")
                    }
                } else {
                    LazyColumn {
                        items(uiState.records) {
                            RelicItem(it) {
                                onSelectRecord(it.id)
                            }
                        }
                    }
                }

            }
        }

    }
}

@Composable
private fun RelicItem(relic: RecordModel, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(6.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_image_basic),
            contentDescription = "image relic",
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(text = relic.numbering, style = MaterialTheme.typography.titleLarge)
            Text(text = relic.group, style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordListUIPreview() {
    RelicRegistryTheme {
        RecordListUI(RecordUiState.Loading) {}
    }
}