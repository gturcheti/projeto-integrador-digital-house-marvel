package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroViewPagerBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.tryToLoadImage
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters.HeroViewPagerAdapter
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.HeroViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.Result
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroVO

class HeroViewPagerFragment : Fragment(R.layout.fragment_hero_view_pager) {

    private lateinit var binding: FragmentHeroViewPagerBinding
    private lateinit var hero: HeroVO
    private var fav: Boolean = false
    private val viewModel by viewModels<HeroViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()

    }

    private fun setupViews() {
        viewModel.fetchCharacterById(requireContext())
        configuraViewPager()
        configuraTabLayout()
        setupFavoritesFab()
    }

    private fun setupFavoritesFab() {
        binding.heroiFavoriteFabBtn.setOnClickListener {
            if (fav) viewModel.removeFavoriteHero(requireContext(), hero)
            else viewModel.saveFavoriteHero(requireContext(), hero)
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

        viewModel.favorite.observe(viewLifecycleOwner) { boo ->
            if (boo) {
                fav = true
                binding.heroiFavoriteFabBtn.setImageResource(R.drawable.ic_favorite)

            } else {
                fav = false
                binding.heroiFavoriteFabBtn.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    private fun configuraViewPager() {
        binding = FragmentHeroViewPagerBinding.bind(requireView())
        val heroiAdapter = HeroViewPagerAdapter(fragmentManager = childFragmentManager)
        val heroiBiografia = HeroBiographyFragment.criar()
        val heroiFilmes = HeroComicsFragment.criar()
        heroiAdapter.addFragment(heroiBiografia, "Biography")
        heroiAdapter.addFragment(heroiFilmes, "Comics")
        binding.heroiViewPager.adapter = heroiAdapter

    }

    private fun configuraTabLayout() {
        binding.heroiTabLayout.setupWithViewPager(
            binding.heroiViewPager
        )
    }

    private fun showContent(heroVO: HeroVO) {
        updateLoadingView(false)
        hero = heroVO
        updateViews(heroVO)
    }

    private fun showError() {
        updateLoadingView(false)
        requireContext().toast("Something went wrong, please try again.")
    }

    private fun showLoading() {
        updateLoadingView(true)
    }

    private fun updateViews(character: HeroVO) {
        binding.heroiImagemCapa.tryToLoadImage(character.image)
    }

    private fun updateLoadingView(isLoadingVisible: Boolean) {
        binding.heroImgProgressbar.isVisible = isLoadingVisible
    }

}