package com.gilbersoncampos.relicregistry.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Session(modifier: Modifier=Modifier,title:String,edit:(()->Unit)?=null, content:@Composable ()->Unit){
    Column(modifier) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text =title, style = MaterialTheme.typography.titleLarge)
            edit?.let {
                IconButton(it) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                }
            }
        }
        content()
    }
}
@Composable
fun SubSession(title:String, content:@Composable ()->Unit){
    Column(Modifier.fillMaxWidth()) {
        Text(text =title, style = MaterialTheme.typography.titleMedium)
        content()
    }
}
@Composable
@Preview
fun SessionPreview(){
    Session(title = "Sessão", edit = {}) {
        
    }
}