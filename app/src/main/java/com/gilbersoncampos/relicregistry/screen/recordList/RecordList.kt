package com.gilbersoncampos.relicregistry.screen.recordList

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gilbersoncampos.relicregistry.R
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.ui.components.AlertDialogCustom
import com.gilbersoncampos.relicregistry.ui.components.HeaderActionSelect
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme

@Composable
fun RecordListScreen(
    viewModel: RecordListViewModel = hiltViewModel(),
    navigateToEditRecord: (Long) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    RecordListUI(
        uiState,
        onClickRecord = navigateToEditRecord,
        onSelectRecord = viewModel::selectRecords,
        removeListRecords = viewModel::removeRecordsSelected,
        getBitmap = viewModel::getImage,
        syncRecords = viewModel::syncRecords
    )

}

@Composable
fun RecordListUI(
    uiState: RecordUiState,
    onClickRecord: (Long) -> Unit,
    onSelectRecord: (CatalogRecordModel) -> Unit,
    removeListRecords:()->Unit,
    getBitmap: (String) -> Bitmap,
    syncRecords:()-> Unit
) {
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
                var showDialog by remember { mutableStateOf(false) }
                val sizeSelected= uiState.records.size
                if (showDialog) {
                    AlertDialogCustom(
                        title = "Excluir registros",
                        text = "Deseja excluir $sizeSelected ${if(sizeSelected==1)"registro" else "registros"}?",
                        onDismiss = { showDialog = false },
                        onConfirm = {
                            showDialog = false
                            removeListRecords()
                        })
                }
                if (uiState.records.isEmpty()) {
                    HeaderActionSelect(uiState.recordsSelected.size, actionSync = syncRecords,actionRemove = {showDialog=true})

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Nenhuma ficha encontrada, Adicione clicando no botão abaixo")
                    }
                } else {

                    HeaderActionSelect(uiState.recordsSelected.size, actionSync = syncRecords,actionRemove = {showDialog=true})

                    LazyColumn {
                        items(uiState.records) {
                            RelicItem(
                                it,
                                getBitmap = getBitmap,
                                isSelected = uiState.recordsSelected.contains(it),
                                onLongPress = {
                                    onSelectRecord(it)
                                }) {
                                if (uiState.recordsSelected.isNotEmpty()) {
                                    onSelectRecord(it)
                                    return@RelicItem
                                }
                                onClickRecord(it.id)
                            }
                        }
                    }
                }

            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RelicItem(
    relic: CatalogRecordModel,
    getBitmap: (String) -> Bitmap,
    isSelected: Boolean = false,
    onLongPress: () -> Unit,
    onClick: () -> Unit,
) {
    val haptics = LocalHapticFeedback.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .combinedClickable(onLongClick = {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            onLongPress()
        }) { onClick() }
        .background(if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background)
        .padding(6.dp)) {
        when{
            isSelected->{
                Image(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "image relic checked",
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray)
                )
            }
            relic.listImages.isEmpty()->{
                Image(
                    painter = painterResource(id = R.drawable.ic_image_basic),
                    contentDescription = "image relic",
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray)
                )
            }
            else->{
                Image(
                    bitmap = getBitmap(relic.listImages[0]).asImageBitmap(),
                    contentDescription = "image relic",
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray)
                )
            }
        }

        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(text = relic.identification, style = MaterialTheme.typography.titleLarge)
            Text(text = relic.group, style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordListUIPreview() {
    RelicRegistryTheme {
        RecordListUI(RecordUiState.Loading, {}, {},{}, { BitmapFactory.decodeFile("") },{})
    }
}