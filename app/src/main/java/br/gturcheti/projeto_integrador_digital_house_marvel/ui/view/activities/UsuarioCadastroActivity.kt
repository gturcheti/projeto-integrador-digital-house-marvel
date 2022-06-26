package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.vaiPara
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.UsuarioCadastroViewModel

class UsuarioCadastroActivity : AppCompatActivity(R.layout.activity_usuario_cadastro){

    private val viewModel: UsuarioCadastroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        configuraBtnCadastrar()
    }

    private fun setupObservers() {
        viewModel.novoUsuario.observe(this) {
            viewModel.cadastraUsuario(this)
            vaiPara(LoginActivity::class.java)
            finish()
        }
    }

    private fun configuraBtnCadastrar() {
        findViewById<Button>(R.id.activity_usuario_cadastro_cadastrar_btn).setOnClickListener {
          criaUsuario()
        }
    }

    private fun criaUsuario(){
        val email = findViewById<TextView>(R.id.activity_usuario_cadastro_email).text.toString()
        val nome = findViewById<TextView>(R.id.activity_usuario_cadastro_nome).text.toString()
        val senha = findViewById<TextView>(R.id.activity_usuario_cadastro_senha).text.toString()
        viewModel.criaUsuario(email, nome, senha)
    }

}