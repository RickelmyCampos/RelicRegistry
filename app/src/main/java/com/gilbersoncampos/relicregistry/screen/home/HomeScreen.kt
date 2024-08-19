package com.gilbersoncampos.relicregistry.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gilbersoncampos.relicregistry.R
import com.gilbersoncampos.relicregistry.data.model.RelicModel

@Composable
fun HomeScreen(){
    val relic = RelicModel("1", "teste", "teste", "teste", "teste")
    val listRelics =
        listOf(relic, relic, relic, relic, relic, relic, relic, relic, relic, relic)
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(listRelics) {
                RelicItem(it)
            }
        }
    }
}
@Composable
private fun RelicItem(relic: RelicModel) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { }
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
            Text(text = relic.name, style = MaterialTheme.typography.titleLarge)
            Text(text = relic.description, style = MaterialTheme.typography.titleSmall)
        }
    }
}