package com.gilbersoncampos.relicregistry.exceptions

sealed class AppException (override val message:String):Exception(message){
    data object FilterNotFoundException:AppException(message = "Filtro não encontrado")

}