package br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.characters


data class DataDTO(
    val results : List<CharacterDTO>,
    val total : String,
)
