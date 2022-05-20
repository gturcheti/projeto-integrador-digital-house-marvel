package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.gturcheti.projeto_integrador_digital_house_marvel.database.AppDatabase
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.ActivityMainBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.vaiPara
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.dataStore
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()

    }

    fun setupListeners() {
        binding.mainActivityListaHeroiBtn.setOnClickListener{
            vaiPara(HeroiListaActivity::class.java)
        }

        binding.mainActivityLoginBtn.setOnClickListener {
            vaiPara(LoginActivity::class.java)
        }

    }


}