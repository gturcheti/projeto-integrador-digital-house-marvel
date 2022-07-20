package br.gturcheti.projeto_integrador_digital_house_marvel.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao.CharacterDAO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao.UserDAO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.Character
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.User
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.relations.UserCharacterCrossRef
import kotlinx.coroutines.CoroutineScope


@Database(
    entities = [
        Character::class,
        User::class,
        UserCharacterCrossRef::class,
    ],
    version = 1,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDAO(): CharacterDAO

    abstract fun userDAO(): UserDAO

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "marvelapp.db"
            ).build().also {
                db = it
            }
        }
    }
}