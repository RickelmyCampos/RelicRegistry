package com.gilbersoncampos.relicregistry

import com.gilbersoncampos.relicregistry.data.model.DropdownData


object Constants {
    val formTypes = listOf(
        "Antropomorfo",
        "Zoomorfo",
        "Antropozoomorfo",
        "Cabeça de estatueta",
        "Corpo de estatueta",
        "Não identificado",
    )
    val formCondition = listOf(
        "Inteiro",
        "Fragmanetado",
        "Reconstituido",
    )
    val formGeneralBodyShape = listOf(
        "Sólida",
        "Oca",
        "Naturalista",
        "Estilizada",
        "Cilíndrica",
        "Globular",
        "Trapezoidal",
        "Fálica",
    )
    val upperLimbs = listOf(
        "Cabeça",
        "Olhos",
        "Nariz",
        "Orelhas",
        "Boca",
        "Pescoço",
        "Ombros",
        "Tórax",
        "Seios",
        "Umbigo",
        "Braços",
        "Mãos",
        "Dedos",
    )
    val lowerLimbs = listOf(
        "Nadegas",
        "Ânus",
        "Pernas",
        "Pés",
        "Dedos",
    )
    val bodyPosition = listOf(
        "Em pé (Frontal)",
        "Em pé (Perfil)",
        "Sentado",
        "Perspectiva dual",
        "Acocorado",
        "Braços afastados do corpo",
        "Braços colados ao corpo",
    )
    val otherFormalAttributes = listOf(
        "Lóbulos alargados",
        "Tangas",
        "Vestimentas",
        "Adornos",
        "Diademas",
        "Penteado",
        "Máscara",
        "Emblemas",
    )
    val burn = listOf(
        "Oxidante", "Redutora", "Núcleo Redutor",
        "Oxidação interna/externa", "Redução interna/externa", "Não Identificada"
    )
    val antiplastic = listOf(
        "Mineral", "Quartzo", "Mica", "Óxido de ferro",
        "Carvão", "Caco moído", "Caraipé", "Concha", "Cauixi"
    )
    val fabricationTechnique = listOf(
        "Acordelado ou roleitado", "Modelado", "Placas",
        "Moldado", "Pint-Finger"
    )
    val fabricationMarks = listOf(
        "Impressão", "Marca de queima"
    )
    val usageMarks = listOf(
        "Crosta carbônica", "Fuligem", "Resina",
        "Ranhuras", "Não possui", "Outra"
    )
    val surfaceTreatment = listOf(
        "Alisado",
        "Polimento",
        "Brunidura",
        "Resina",
        "Escovado",
        "Engobo",
        "Barbotina",
        "Não possui"
    )
    val surfaceTreatmentET = listOf(
        "Alisado", "Polimento", "Brunidura", "Resina",
        "Escovado", "Engobo", "Barbotina", "Não possui"
    )
    val location = listOf(
        "Externa", "Interna", "Ambos"
    )
    val decorationType = listOf(
        "Pintura", "Plástica", "Grafismo"
    )
    val paintColorFI = listOf(
        "Branco", "Preto", "Creme/Bege", "Outra", "Não identificado"
    )
    val plasticDecoration = listOf(
        "Incisão", "Excisão", "Aplique de olhos",
        "Aplique mamiforme", "Ponteado", "Digitado",
        "Orifício vazado", "Orifício não vazado", "Alça"
    )
    val uses = listOf(
        "Chocalho", "Recipiente", "Instrumento musical",
        "Amuleto", "Cachimbo", "Arma", "Outra", "Não identificada"
    )


}