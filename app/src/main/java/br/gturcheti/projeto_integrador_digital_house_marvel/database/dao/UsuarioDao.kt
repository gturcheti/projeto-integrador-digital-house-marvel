package br.gturcheti.projeto_integrador_digital_house_marvel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.gturcheti.projeto_integrador_digital_house_marvel.model.Usuario
import kotlinx.coroutines.flow.Flow


@Dao
interface UsuarioDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg usuario: Usuario)

    @Query(""" 
        SELECT * FROM Usuario 
        WHERE email = :usuarioEmail
        AND senha = :usuarioSenha""")
    suspend fun autentica(
        usuarioEmail: String,
        usuarioSenha: String
    ): Usuario?

    @Query("SELECT * FROM Usuario WHERE email = :usuarioEmail")
    fun buscaUsuarioPorId (usuarioEmail: String) : Flow<Usuario>

}