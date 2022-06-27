package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters.HeroiViewPagerAdapter
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroiBiografiaBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroiViewPagerBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.tryToLoadImage
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class HeroiViewPagerFragment : Fragment(R.layout.fragment_heroi_view_pager) {

    private lateinit var viewPagerBinding: FragmentHeroiViewPagerBinding

    private val heroi: HeroiVO? by lazy {
        arguments?.getSerializable(HEROI_KEY)?.let { it as HeroiVO}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()

    }

    private fun setupViews() {
        configuraViewPager()
        configuraTabLayout()
    }

    private fun configuraViewPager() {
        viewPagerBinding = FragmentHeroiViewPagerBinding.bind(requireView())
        val heroiAdapter = HeroiViewPagerAdapter(fragmentManager = childFragmentManager)
        val heroiBiografia = HeroiBiografiaFragment.criar()
        val heroiFilmes = HeroiFilmesFragment.criar()
        heroiAdapter.addFragment(heroiBiografia, "Biografia")
        heroiAdapter.addFragment(heroiFilmes, "Filmes")
        viewPagerBinding.heroiViewPager.adapter = heroiAdapter
        viewPagerBinding.heroiImagemCapa.tryToLoadImage(heroi?.image)
    }

    private fun configuraTabLayout() {
        viewPagerBinding.heroiTabLayout.setupWithViewPager(
            viewPagerBinding.heroiViewPager)
    }

    companion object {
        private const val HEROI_KEY = "heroi"
        fun buildBundle(heroi: HeroiVO) = bundleOf(HEROI_KEY to heroi)
    }

}