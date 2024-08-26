package com.gilbersoncampos.relicregistry.extensions

fun String.toOnlyFloat(): Float {
    return try {
        this.toFloat()
    }catch (e:NumberFormatException){
        0f
    }
}