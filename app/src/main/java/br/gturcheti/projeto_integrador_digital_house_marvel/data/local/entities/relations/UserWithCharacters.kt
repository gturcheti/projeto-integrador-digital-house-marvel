package br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.Character
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.User

data class UserWithCharacters(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userEmail",
        entityColumn = "characterId",
        associateBy = Junction(UserCharacterCrossRef::class)
    )
    val characters: List<Character>
)