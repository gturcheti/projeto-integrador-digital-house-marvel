package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.ActivityMainBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.vaiPara

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()

    }

    fun setupListeners() {


        findViewById<Button>(R.id.main_activity_lista_heroi_btn).setOnClickListener{
            vaiPara(HeroiListaActivity::class.java)
        }

        findViewById<ImageButton>(R.id.main_activity_login_btn).setOnClickListener {
            vaiPara(LoginActivity::class.java)
        }

    }


}