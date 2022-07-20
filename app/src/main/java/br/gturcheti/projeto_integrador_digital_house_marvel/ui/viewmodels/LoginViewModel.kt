package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gturcheti.projeto_integrador_digital_house_marvel.data.AppDatabase
import br.gturcheti.projeto_integrador_digital_house_marvel.data.UserRepository
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao.CharacterDAO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao.UserDAO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val GOOGLE_REQUEST_CODE = 1001

    private val repository = UserRepository()

    private val _userSignIn: MutableLiveData<Result<Unit>> = MutableLiveData()
    val userSignIn: LiveData<Result<Unit>> = _userSignIn

    private val _newUser: MutableLiveData<Result<Unit>> = MutableLiveData()
    val newUser: LiveData<Result<Unit>> = _newUser

    fun signInWithGoogleIntent(activity: Activity) : Intent {
        return GoogleSignIn.getClient(activity,repository.gso).signInIntent
    }

    fun loginAuth(email: String, password: String) {
        _userSignIn.value = Result.Loading
        viewModelScope.launch {
            repository.signInWithAuth(email, password).let { boo ->
                if (boo) _userSignIn.value = Result.Success(Unit)
                else _userSignIn.value = Result.Error
            }
        }
    }

    fun loginGoogle(firebaseCredencial: AuthCredential, task: Task<GoogleSignInAccount>) {
        _userSignIn.value = Result.Loading
        viewModelScope.launch {
            repository.siginInWithGoogleOnFirebase(firebaseCredencial).let { boo ->
                if (boo) repository.handleSignInResult(task).let { boo ->
                    if (boo) _userSignIn.value = Result.Success(Unit)
                    else _userSignIn.value = Result.Error
                }
                else _userSignIn.value = Result.Error
            }
        }
    }

    fun createUser(user: User, password: String) {
        _newUser.value = Result.Loading
        viewModelScope.launch {
            repository.createUser(user, password).let { boo ->
                if (boo) {
                    _newUser.value = Result.Success(Unit)
                }
                else _newUser.value = Result.Error
            }
        }
    }
}