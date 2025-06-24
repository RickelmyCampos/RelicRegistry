package com.gilbersoncampos.relicregistry.data.model

import java.time.LocalDateTime

data class HistoricSyncModel(
    val id: Long,
    val status: StatusSync,
    val data: String,
    val errorMessage: String?=null,
    val startIn: LocalDateTime,
    val endIn: LocalDateTime?=null,
)
enum class StatusSync(val nameString: String){
    SUCCESS("Sucesso"),
    ERROR("Erro"),
    LOADING("Carregando")
}
