package com.gilbersoncampos.relicregistry.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gilbersoncampos.relicregistry.screen.recordList.RecordListScreen
import com.gilbersoncampos.relicregistry.screen.recordList.RecordListUI
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme

@Composable
fun HomeScreen(navigateToEditRecord:(Int)->Unit) {
    RecordListScreen(navigateToEditRecord=navigateToEditRecord)
}

@Composable
fun HomeUi() {
    val listMenu = listOf("criar", "listar")
    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(listMenu) {
                MenuItem(it)
            }
        }
    }
}

@Composable
fun MenuItem(name: String) {
    Card(modifier = Modifier.padding(8.dp).size(100.dp), onClick = { /*TODO*/ }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeUiPreview() {
    RelicRegistryTheme {
        HomeUi()
    }
}