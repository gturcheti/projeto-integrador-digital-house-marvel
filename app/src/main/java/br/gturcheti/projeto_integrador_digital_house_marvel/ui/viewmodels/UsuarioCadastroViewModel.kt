package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gturcheti.projeto_integrador_digital_house_marvel.database.AppDatabase
import br.gturcheti.projeto_integrador_digital_house_marvel.database.dao.UsuarioDao
import br.gturcheti.projeto_integrador_digital_house_marvel.model.Usuario
import kotlinx.coroutines.launch

class UsuarioCadastroViewModel : ViewModel(){

    private val _novoUsuario: MutableLiveData<Usuario> = MutableLiveData()
    val novoUsuario: LiveData<Usuario> = _novoUsuario


    fun criaUsuario(usuarioEmail: String, usuarioNome: String, senha: String) {
        _novoUsuario.value = Usuario(usuarioEmail, usuarioNome, senha)
    }

    fun cadastraUsuario(context:Context) {
        viewModelScope.launch {
            _novoUsuario.value?.let { usuario ->
                getDao(context).salva(usuario)
            }
        }
    }

    private fun getDao(context: Context) : UsuarioDao {
        return AppDatabase.instancia(context).usuarioDao()
    }
}