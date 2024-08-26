package com.gilbersoncampos.relicregistry.extensions

fun String.toOnlyFloat(): Float {
    return try {
        this.toFloat()
    }catch (e:NumberFormatException){
        0f
    }
}
fun String.hasOnlyNumber():Boolean{
    // Define a regular expression to match non-alphanumeric characters and spaces
    val regex = Regex("[0-9]+")
    return regex.matches(this)
}