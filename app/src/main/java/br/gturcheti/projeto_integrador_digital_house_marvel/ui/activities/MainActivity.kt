package br.gturcheti.projeto_integrador_digital_house_marvel.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val botao = binding.mainActivityListaHeroiBtn


        botao.setOnClickListener{
            val intent = Intent(this, HeroiListaActivity::class.java)
            startActivity(intent)
        }

    }
}