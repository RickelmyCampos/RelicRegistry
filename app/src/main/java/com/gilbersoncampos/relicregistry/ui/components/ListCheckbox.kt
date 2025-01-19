package com.gilbersoncampos.relicregistry.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gilbersoncampos.relicregistry.extensions.getNameTranslated

@Composable
fun <T> ListCheckbox(
    list: List<T>,
    listSelected: List<T>,
    onCheckedChange: (List<T>, Boolean) -> Unit
) {
    list.forEach { item ->
        val mListSelected = listSelected.toMutableList()

        TextCheckbox(
            { selected ->
                if (!selected) {
                    mListSelected.remove(item)
                } else {
                    mListSelected.add(item)
                }
                onCheckedChange(mListSelected, true)
            }, when (item) {
                is Enum<*> -> item.getNameTranslated()
                else -> item.toString()
            }, listSelected.contains(item)
        )
    }
}

@Composable
fun TextCheckbox(
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!selected)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = selected, onCheckedChange = { checked ->
            onCheckedChange(checked)
        })
        Text(text = label)
    }
}