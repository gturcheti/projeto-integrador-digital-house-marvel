package br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.comics


import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.ThumbnailDTO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.UrlsDTO
import com.google.gson.annotations.SerializedName

class ComicDTO(
    val id: Int,
    val title: String,
    @SerializedName("thumbnail")
    val image: ThumbnailDTO,
    val urls: List<UrlsDTO>,
)