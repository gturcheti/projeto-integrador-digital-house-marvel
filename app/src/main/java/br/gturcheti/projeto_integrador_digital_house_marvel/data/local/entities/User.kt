package br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey
    val userEmail: String,
    val name: String,
) : Parcelable
