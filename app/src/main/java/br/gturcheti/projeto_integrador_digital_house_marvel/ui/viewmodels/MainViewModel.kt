package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gturcheti.projeto_integrador_digital_house_marvel.data.AppDatabase
import br.gturcheti.projeto_integrador_digital_house_marvel.data.UserRepository
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao.UserDAO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.User
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel () : ViewModel() {

    private val repository = UserRepository()

    private val _userLogged: MutableLiveData<Boolean> = MutableLiveData()
    val userLogged: LiveData<Boolean> = _userLogged

    fun checkForUsers(context: Context) {
        _userLogged.value = false
        viewModelScope.launch {
            repository.currentUser.collect { firebaseuser ->
                firebaseuser?.let {
                    checkForLocalUser(context, firebaseuser.email.toString()).let { boo ->
                        if (boo) _userLogged.value = true
                        else registerLocalUser(
                            context,
                            User(
                                firebaseuser.email.toString(),
                                firebaseuser.displayName.toString()
                            )
                        ).also {
                            _userLogged.value = true
                        }
                    }
                }
                Log.i("MAIN_ACTIVITY", "checkForUsers: ${firebaseuser?.displayName.toString()} ")
            }
        }
    }

    fun signOut(context: Context){
        viewModelScope.launch{
            repository.signOutAuth()
            context.toast("Signned Out")
            _userLogged.value = false
        }
    }

    private fun checkForLocalUser(context: Context, userEmail: String): Boolean {
        var user: User? = null
        viewModelScope.launch(Dispatchers.IO) {
            getDao(context).findLocalUser(userEmail)?.let {
                user = it
            }
        }
        return user != null
    }

    private fun registerLocalUser(context: Context, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            getDao(context).insertUser(user)
        }
    }

    private fun getDao(context: Context): UserDAO {
        return AppDatabase.instancia(context).userDAO()
    }


}