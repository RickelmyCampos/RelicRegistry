package com.gilbersoncampos.relicregistry.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
import com.gilbersoncampos.relicregistry.Constants.DATE_FORMATER
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.toOnlyFloat(): Float {
    return try {
        this.toFloat()
    } catch (e: NumberFormatException) {
        0f
    }
}

fun String.hasOnlyNumber(): Boolean {
    // Define a regular expression to match non-alphanumeric characters and spaces
    val regex = Regex("[0-9]+")
    return regex.matches(this)
}
fun String.toLocalDateTime(): LocalDateTime? {
    var nValue=this
    if(this.isDigitsOnly()){
        nValue=
            Instant.ofEpochMilli(this.toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(
                DATE_FORMATER
            )
    }
    return try{nValue.let { LocalDateTime.parse(it, DATE_FORMATER) }}catch (e:Exception){null}
}



