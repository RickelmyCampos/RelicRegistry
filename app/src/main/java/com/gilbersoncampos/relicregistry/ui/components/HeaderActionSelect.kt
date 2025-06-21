package com.gilbersoncampos.relicregistry.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme

@Composable
fun HeaderActionSelect(sizeSelected:Int,actionRemove:()->Unit,actionSync:()-> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        if(sizeSelected>0){
            Text("$sizeSelected ${if(sizeSelected==1)"selecionado" else "selecionados"}")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(actionSync) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "sync_refresh icon")
        }
        if(sizeSelected>0){
            IconButton(actionRemove) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "remove icon")
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun HeaderActionSelectPreview(){
    RelicRegistryTheme {
        HeaderActionSelect(2,{}) { }
    }
}
@Composable
@Preview(showBackground = true)
fun HeaderActionSelectNoItemsSelectedPreview(){
    RelicRegistryTheme {
        HeaderActionSelect(0,{}) { }
    }
}