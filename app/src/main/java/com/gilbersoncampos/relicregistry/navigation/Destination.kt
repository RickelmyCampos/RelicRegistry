package com.gilbersoncampos.relicregistry.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.gilbersoncampos.relicregistry.R

sealed class Destination(val route: String, val name: String) {
    data object Home : Destination("home", "Home")
    data object EditRecord : Destination("edit_record/{id_record}", "Editar Ficha")
    data object ListRecord : Destination("list_record", "Fichas")
    data object Settings : Destination("config", "Configuração")
    data object Form : Destination("form", "Formulário")
    data object Charts : Destination("charts", "Gráficos")
    data object HitoricSync : Destination("historic_sync", "Histórico")
}

val listBottomNavigation: List<Destination> =
    listOf(Destination.ListRecord,Destination.Charts, Destination.Settings)

@Composable
fun Destination.getIcon(): ImageVector {
    return when (this) {
        Destination.EditRecord -> Icons.Default.Edit
        Destination.Home -> Icons.Default.Home
        Destination.ListRecord -> Icons.Default.Home
        Destination.Settings -> Icons.Default.Settings
        Destination.Form -> Icons.Default.Settings
        Destination.Charts -> ImageVector.vectorResource(R.drawable.ic_chart)
        else -> Icons.Default.Home
    }
}