package br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo

import java.io.Serializable

data class HeroVO(
    val id : String,
    val name : String,
    val description: String,
    val image: String,
    val url: String,
) : Serializable