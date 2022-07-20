package br.gturcheti.projeto_integrador_digital_house_marvel.data

import android.util.Log
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao.UserDAO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepository {

    private val auth = Firebase.auth

    private val _currentUser: MutableStateFlow<FirebaseUser?> = MutableStateFlow(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("112999840537-9tcn1dv774ehne3rej1f3ta69dfsniig.apps.googleusercontent.com")
        .requestEmail()
        .build()

    private val userDataSource = Firebase.database
    private val userRef = userDataSource.getReference("users")

    private suspend fun createUserOnDataBase(user: User): Boolean =
        withContext(Dispatchers.IO) {
            auth.currentUser?.let { firebaseUser ->
                suspendCoroutine {
                    userRef.child(firebaseUser.uid).setValue(user)
                        .addOnCompleteListener { firebaseTask ->
                            it.resume(firebaseTask.isSuccessful)
                        }
                }
            } ?: false
        }

    private suspend fun createUserOnAuth(user: User, password: String): Boolean =
        withContext(Dispatchers.IO) {
            suspendCoroutine {
                auth.createUserWithEmailAndPassword(user.userEmail, password)
                    .addOnCompleteListener() { authTask ->
                        it.resume(authTask.isSuccessful)
                    }
            }
        }

    suspend fun createUser(user: User, password: String): Boolean =
        withContext(Dispatchers.IO) {
            val resultado = createUserOnAuth(user, password)
            if (resultado) {
                createUserOnDataBase(user)
            } else {
                resultado
            }
        }

    suspend fun signInWithAuth(email: String, password: String): Boolean =
        withContext(Dispatchers.IO) {
            suspendCoroutine {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { authTask ->
                        it.resume(authTask.isSuccessful)
                    }
            }
        }


    suspend fun siginInWithGoogleOnFirebase(firebaseCredential: AuthCredential): Boolean =
        withContext(Dispatchers.IO) {
            suspendCoroutine {
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { authTask ->
                        it.resume(authTask.isSuccessful)
                    }
            }
        }

    suspend fun handleSignInResult(task: Task<GoogleSignInAccount>): Boolean =
        withContext(Dispatchers.IO) {
            suspendCoroutine {
                val account = task.getResult(ApiException::class.java)
                val userId = account.id.toString()
                Log.i("USER_REPOSITORY", "handleSignInResult: userid -> $userId")
                val user = User(
                    userEmail = account.email.toString(),
                    name = account.displayName.toString()
                )

                userRef.child(userId).setValue(user).addOnCompleteListener { firebaseTask ->
                    it.resume(firebaseTask.isSuccessful)
                }
            }
        }

    suspend fun signOutAuth(){
        withContext(Dispatchers.IO){
            auth.signOut()
        }
    }

}