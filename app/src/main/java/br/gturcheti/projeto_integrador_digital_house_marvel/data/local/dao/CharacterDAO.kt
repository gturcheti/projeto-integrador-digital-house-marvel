package br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao

import androidx.room.*
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.Character
import kotlinx.coroutines.flow.Flow


@Dao
interface CharacterDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocalHero(vararg characters: Character)

    @Query("SELECT * FROM Character")
    fun findAllLocalCharacters() : Flow<List<Character>>

    @Query("SELECT * FROM Character WHERE characterId = :id")
    fun findLocalCharacterById (id: String) : Flow<Character?>

    @Delete
    suspend fun deleteCharacter(character: Character)

}