package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.ActivityListaHeroiBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.model.Heroi
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters.HeroiRecyclerViewAdapter
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodel.HeroiListaViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodel.Result
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO

class HeroiListaActivity() : AppCompatActivity() {

    //    private val dao = HeroiDao()
    private val adapter = HeroiRecyclerViewAdapter(this)
    private val viewModel by viewModels<HeroiListaViewModel>()

    private val binding by lazy {
        ActivityListaHeroiBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.fetchHerois()
        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.herois.observe(this) {
            when (it) {
                is Result.Success -> showContent(it.data)
            }

        }
    }

    private fun showContent(herois: List<HeroiVO>) {
        adapter.atualiza(herois)
    }

//    private fun showError() {
//        updateLoadingView(false)
//
//    }
//
//    private fun showLoading() {
//        updateLoadingView(true)
//    }
//
//    private fun updateLoadingView(isLoadingVisible: Boolean) {
//        with(binding) {
//            activityListaHeroiProgressbar.isVisible = isLoadingVisible
//            activityListaHeroiRecyclerview.isVisible = activityListaHeroiProgressbar.isVisible.not()
//        }
//
//    }

    private fun setupViews() {
        configuraRecyclerView()
    }

//    override fun onResume() {
//        super.onResume()
//        adapter.atualiza()
//    }

    fun vaiParaHeroi(heroi: Heroi) {
        val intent = Intent(this, HeroiActivity::class.java)
        intent.putExtra("heroi", heroi)
        startActivity(intent)
    }

    fun configuraRecyclerView() {
        binding.activityListaHeroiRecyclerview.adapter = adapter
        adapter.heroiItemListener = {
            vaiParaHeroi(it)
        }
    }
}