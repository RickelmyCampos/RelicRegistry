package com.gilbersoncampos.relicregistry

import com.gilbersoncampos.relicregistry.data.model.DropdownData


object Constants {
    val formTypes = listOf(
        DropdownData(0, "Antropomorfo"),
        DropdownData(1, "Zoomorfo"),
        DropdownData(2, "Antropozoomorfo"),
        DropdownData(3, "Cabeça de estatueta"),
        DropdownData(4, "Corpo de estatueta"),
        DropdownData(5, "Não identificado"),
    )
}