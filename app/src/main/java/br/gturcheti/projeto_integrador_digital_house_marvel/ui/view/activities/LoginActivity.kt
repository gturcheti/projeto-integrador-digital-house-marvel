package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.vaiPara
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.dataStore
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.usuarioLogadoPreferences
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
        setupObservers()
    }

    fun setupListeners() {
        configuraBtnEntrar()
        configuraBtnCadastrar()
    }

    fun setupObservers() {
        viewModel.userLogged.observe(this) { usuarioLogado ->
            usuarioLogado?.let {
                lifecycleScope.launch {
                    dataStore.edit { preferences ->
                        preferences[usuarioLogadoPreferences] = usuarioLogado.email
                        vaiPara(MainActivity::class.java)
                        finish()
                    }
                }
            }
        }
    }


    private fun configuraBtnCadastrar() {
        findViewById<Button>(R.id.activity_login_registrar_btn).setOnClickListener {
            vaiPara(UsuarioCadastroActivity::class.java)
        }
    }

    fun configuraBtnEntrar() {
        findViewById<Button>(R.id.activity_login_logar_btn).setOnClickListener {
            val usuarioEmail = findViewById<TextView>(R.id.activity_login_email).text.toString()
            val senha = findViewById<TextView>(R.id.activity_login_senha).text.toString()
            viewModel.autentica(this, usuarioEmail, senha)

        }
    }

}