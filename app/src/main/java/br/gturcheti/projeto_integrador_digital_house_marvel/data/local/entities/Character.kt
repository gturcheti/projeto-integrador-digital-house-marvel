package br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Character(
    @PrimaryKey
    val characterId: String,
    val name: String,
    val description: String,
    val image: String,
    val url:String,
) : Parcelable