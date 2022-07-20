package br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.Character
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.User

data class CharacterWithUsers(
    @Embedded val character: Character,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "userEmail",
        associateBy = Junction(UserCharacterCrossRef::class)
    )
    val users: List<User>
)