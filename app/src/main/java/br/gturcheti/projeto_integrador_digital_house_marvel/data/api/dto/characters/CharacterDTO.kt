package br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.characters

import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.ThumbnailDTO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.UrlsDTO
import com.google.gson.annotations.SerializedName

data class CharacterDTO(
    val id : Int,
    val name : String,
    val description: String,
    @SerializedName("thumbnail")
    val image: ThumbnailDTO,
    val urls: List<UrlsDTO>,
)
