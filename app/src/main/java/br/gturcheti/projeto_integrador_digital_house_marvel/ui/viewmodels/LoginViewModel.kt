package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gturcheti.projeto_integrador_digital_house_marvel.database.AppDatabase
import br.gturcheti.projeto_integrador_digital_house_marvel.database.local.dao.UsuarioDao
import br.gturcheti.projeto_integrador_digital_house_marvel.model.Usuario
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _userLogged: MutableLiveData<Usuario> = MutableLiveData()

    val userLogged: LiveData<Usuario> = _userLogged

    fun autentica(context: Context, usuarioEmail: String, senha: String) {
        viewModelScope.launch {
            getDao(context).autentica(usuarioEmail, senha)?.let { usuarioAutenticado ->
                _userLogged.value = usuarioAutenticado
                Log.i("AUTENTICA", "$_userLogged")
            }
        }
    }

    private fun getDao(context: Context) : UsuarioDao {
        return AppDatabase.instancia(context).usuarioDao()
    }

}