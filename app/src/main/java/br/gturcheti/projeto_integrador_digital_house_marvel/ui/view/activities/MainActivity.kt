package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.ActivityMainBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.vaiPara
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userLogged.observe(this){boo ->
            if (boo) showUserInfo()
            else vaiPara(LoginActivity::class.java)
        }
    }

    private fun showUserInfo() {
        toast("Bem-Vindo")
    }

    fun setupViews() {
        viewModel.checkForUsers(this)
        with(binding) {
            mainActivityLoginBtn.setOnClickListener {
                viewModel.signOut(this@MainActivity)
            }
            mainActivityListaHeroiBtn.setOnClickListener {
                vaiPara(HeroActivity::class.java)
            }
        }
    }
}