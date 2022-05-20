package br.gturcheti.projeto_integrador_digital_house_marvel.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Usuario(
    @PrimaryKey
    val email: String,
    val nome: String,
    val senha: String,
) : Parcelable
