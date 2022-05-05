package br.gturcheti.projeto_integrador_digital_house_marvel.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Heroi(
    val nomeHeroi: String,
    val nomePersonagem: String,
    val biografia: String,
    val imagem: Int
) : Parcelable