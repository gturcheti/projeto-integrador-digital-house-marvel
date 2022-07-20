package br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.relations

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity (primaryKeys = ["userEmail", "characterId"])
@Parcelize
data class UserCharacterCrossRef(
    val userEmail: String,
    val characterId: String,
) : Parcelable
