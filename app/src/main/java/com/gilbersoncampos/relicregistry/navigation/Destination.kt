package com.gilbersoncampos.relicregistry.navigation

sealed class Destination(val route:String,val name:String) {
    data object Home:Destination("home","Home")
    data object EditRecord:Destination("edit_record/{id_record}","Editar Ficha")
}