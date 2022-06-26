package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroiRecyclerViewBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters.HeroiRecyclerViewAdapter
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.HeroiListaViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.Result
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO

class HeroiListaFragment : Fragment(R.layout.fragment_heroi_recycler_view) {

    private lateinit var binding: FragmentHeroiRecyclerViewBinding
    private val viewModel by viewModels<HeroiListaViewModel>()

    private val heroiAdapter = HeroiRecyclerViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchHerois()
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding = FragmentHeroiRecyclerViewBinding.bind(requireView())
        with(binding) {
            rvHeroiLista.adapter = heroiAdapter
            heroiAdapter.heroiItemListener = {
                navigateToHeroiVP(it)
            }
        }
    }

    private fun setupObservers() {
        viewModel.herois.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> showContent(it.data)
            }
        }
    }

    private fun showContent(herois: List<HeroiVO>) {
        heroiAdapter.atualizaHerois(herois)
    }


    fun navigateToHeroiVP(heroi: HeroiVO) {
        val bundle = HeroiViewPagerFragment.buildBundle(heroi)
        findNavController().navigate(R.id.action_heroiVP_to_heroiRV, bundle)
    }

}