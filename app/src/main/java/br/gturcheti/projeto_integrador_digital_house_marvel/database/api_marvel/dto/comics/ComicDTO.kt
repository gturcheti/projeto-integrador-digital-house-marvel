package br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.dto.comics


import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.dto.thumbnail.ThumbnailDTO
import com.google.gson.annotations.SerializedName

class ComicDTO(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("thumbnail")
    val image: ThumbnailDTO,
)