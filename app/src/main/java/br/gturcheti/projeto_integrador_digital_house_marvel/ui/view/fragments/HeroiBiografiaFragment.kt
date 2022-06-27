package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroiBiografiaBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.HeroiViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.Result
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class HeroiBiografiaFragment : Fragment(R.layout.fragment_heroi_biografia) {


    private val viewModel by viewModels<HeroiViewModel>()
    private lateinit var binding: FragmentHeroiBiografiaBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding = FragmentHeroiBiografiaBinding.bind(requireView())
        viewModel.fetchCharacterById(requireContext())
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

    private fun showContent(character: HeroiVO) {
        updateLoadingView(false)
        updateViews(character)
    }

    private fun updateViews(character: HeroiVO) {
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
        fun criar(): HeroiBiografiaFragment = HeroiBiografiaFragment()
    }

}