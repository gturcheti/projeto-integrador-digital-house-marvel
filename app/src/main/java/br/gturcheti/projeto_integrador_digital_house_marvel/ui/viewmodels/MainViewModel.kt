package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.gturcheti.projeto_integrador_digital_house_marvel.database.AppDatabase
import br.gturcheti.projeto_integrador_digital_house_marvel.database.local.dao.UsuarioDao
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.vaiPara
import br.gturcheti.projeto_integrador_digital_house_marvel.model.Usuario
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.userDataStore
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.usuarioLogadoPreferences
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities.LoginActivity

class MainViewModel {

    private val _userLogged: MutableLiveData<Usuario> = MutableLiveData()
    val userLogged: LiveData<Usuario> = _userLogged


    suspend fun isUserLoggedIn(context: Context) {
        context.userDataStore.data.collect { preferences ->
            preferences[usuarioLogadoPreferences]?.let { usuarioEmail ->

            } ?: context.vaiPara(LoginActivity::class.java)
        }
    }


    private fun getDao(context: Context) : UsuarioDao {
        return AppDatabase.instancia(context).usuarioDao()
    }
}