package br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao

import androidx.room.*
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.User
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.relations.UserCharacterCrossRef
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.relations.UserWithCharacters
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun associateCharacterByUser(crossRef: UserCharacterCrossRef)

    @Query("SELECT * FROM User WHERE userEmail= :userEmail")
    fun findLocalUser(userEmail: String) : User?

    @Transaction
    @Query("SELECT * FROM User WHERE userEmail= :userEmail")
    fun getUserWithCharacters(userEmail: String): Flow<List<UserWithCharacters>?>

    @Delete
    suspend fun deleteRelationCharacterByUser(crossRef: UserCharacterCrossRef)
}