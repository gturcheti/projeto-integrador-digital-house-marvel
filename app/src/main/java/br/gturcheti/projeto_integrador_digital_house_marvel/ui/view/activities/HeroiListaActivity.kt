package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.ActivityListaHeroiBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.model.Heroi
import br.gturcheti.projeto_integrador_digital_house_marvel.model.HeroiDao
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.recyclerview.HeroiRecyclerViewAdapter

class HeroiListaActivity() : AppCompatActivity() {

    private val dao = HeroiDao()
    private val adapter = HeroiRecyclerViewAdapter(this)
    private val binding by lazy {
        ActivityListaHeroiBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        dao.add(
            Heroi(
                "Homem de Ferro",
                "Tony Stark",
                "Biografia",
                R.drawable.ironcapa
            )
        )

        dao.add(
            Heroi(
                "Homem de Ferro 2",
                "Tony Stark 2",
                "Biografia 2",
                R.drawable.ironcapa
            )
        )

        dao.add(
            Heroi(
                "Homem de Ferro 3",
                "Tony Stark 3",
                "Biografia 3",
                R.drawable.ironcapa
            )
        )

        configuraRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
    }

    fun vaiParaHeroi(heroi: Heroi) {
        val intent = Intent(this, HeroiActivity::class.java)
        intent.putExtra("heroi", heroi)
        startActivity(intent)
    }

    fun configuraRecyclerView() {
        val recyclerView = binding.activityListaHeroiRecyclerview
        recyclerView.adapter = adapter
        adapter.heroiItemListener = {
            vaiParaHeroi(it)
        }
    }
}