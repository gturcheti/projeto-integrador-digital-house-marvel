package br.gturcheti.projeto_integrador_digital_house_marvel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.gturcheti.projeto_integrador_digital_house_marvel.database.local.dao.UsuarioDao
import br.gturcheti.projeto_integrador_digital_house_marvel.model.Usuario


@Database(
    entities = [
        Usuario::class
    ],
    version = 1,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).build().also {
                db = it
            }
        }
    }
}