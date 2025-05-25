package com.gilbersoncampos.relicregistry.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toBrDateTime(): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")
        this.format(formatter)
    } catch (e: Exception) {
        "Data inválida"
    }
}