package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroBiographyBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.HeroViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.Result
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroVO

class HeroBiographyFragment : Fragment(R.layout.fragment_hero_biography) {


    private val viewModel by viewModels<HeroViewModel>()
    private lateinit var binding: FragmentHeroBiographyBinding
    private lateinit var hero: HeroVO

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding = FragmentHeroBiographyBinding.bind(requireView())
        viewModel.fetchCharacterById(requireContext())
        setupShareButton()
        setupSeeMoreButton()
    }

    private fun setupSeeMoreButton() {
        binding.heroFragmentSaibaMaisBtn.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("O link será aberto em seu navegador, confirmar a operação?")
                .setPositiveButton("Confirmar") { _, _ ->
                    viewModel.seeMoreOnMarvelSiteIntent(requireContext(), hero.url)
                }
                .setNegativeButton("Cancelar") { _, _ ->
                }
                .show()
        }
    }

    private fun setupShareButton() {
        binding.heroFragmentShareBtn.setOnClickListener {
            val message = "My favorite Hero is ${hero.name}, check on Marvel's site for more: ${hero.url}"
            viewModel.shareHeroOnSocialMedia(requireContext(), message)
        }
    }

    private fun setupObservers() {
        viewModel.character.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading()
                is Result.Error -> showError()
                is Result.Success -> showContent(result.data)
            }
        }
    }

    private fun showContent(character: HeroVO) {
        hero = character
        updateLoadingView(false)
        updateViews(character)
    }

    private fun updateViews(character: HeroVO) {
        with(binding) {
            heroiFragmentNomeHeroi.text = character.name
            heroiFragmentDescricao.text = character.description
        }
    }

    private fun showError() {
        updateLoadingView(false)
        requireContext().toast("Something went wrong, please try again.")
    }

    private fun showLoading() {
        updateLoadingView(true)
    }

    private fun updateLoadingView(isLoadingVisible: Boolean) {
        binding.heroBioProgressbar.isVisible = isLoadingVisible
    }

    companion object {
        fun criar(): HeroBiographyFragment = HeroBiographyFragment()
    }

}