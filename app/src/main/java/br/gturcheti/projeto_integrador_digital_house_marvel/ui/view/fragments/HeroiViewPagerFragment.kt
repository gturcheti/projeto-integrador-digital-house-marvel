package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroiViewPagerBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.tryToLoadImage
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters.HeroiViewPagerAdapter
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.HeroiViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.Result
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO

class HeroiViewPagerFragment : Fragment(R.layout.fragment_heroi_view_pager) {

    private lateinit var binding: FragmentHeroiViewPagerBinding
    private val viewModel by viewModels<HeroiViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCharacterById(requireContext())
        setupViews()
        setupObservers()

    }

    private fun setupViews() {
        configuraViewPager()
        configuraTabLayout()

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

    private fun configuraViewPager() {
        binding = FragmentHeroiViewPagerBinding.bind(requireView())
        val heroiAdapter = HeroiViewPagerAdapter(fragmentManager = childFragmentManager)
        val heroiBiografia = HeroiBiografiaFragment.criar()
        val heroiFilmes = HeroiFilmesFragment.criar()
        heroiAdapter.addFragment(heroiBiografia, "Biografia")
        heroiAdapter.addFragment(heroiFilmes, "Filmes")
        binding.heroiViewPager.adapter = heroiAdapter

    }

    private fun configuraTabLayout() {
        binding.heroiTabLayout.setupWithViewPager(
            binding.heroiViewPager
        )
    }

    private fun showContent(character: HeroiVO) {
        updateLoadingView(false)
        updateViews(character)
    }

    private fun showError() {
        updateLoadingView(false)
        requireContext().toast("Something went wrong, please try again.")
    }

    private fun showLoading() {
        updateLoadingView(true)
    }

    private fun updateViews(character: HeroiVO) {
        binding.heroiImagemCapa.tryToLoadImage(character.image)

    }

    private fun updateLoadingView(isLoadingVisible: Boolean) {
        binding.heroImgProgressbar.isVisible = isLoadingVisible
    }

}