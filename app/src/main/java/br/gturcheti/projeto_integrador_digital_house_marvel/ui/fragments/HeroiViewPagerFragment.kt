package br.gturcheti.projeto_integrador_digital_house_marvel.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import br.gturcheti.projeto_integrador_digital_house_marvel.viewpager.HeroiViewPagerAdapter
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import com.google.android.material.tabs.TabLayout

class HeroiViewPagerFragment : Fragment(R.layout.heroi_fragment_view_pager) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val heroiViewPager = view.findViewById<ViewPager>(R.id.heroi_view_pager)
        configuraViewPager(heroiViewPager)

        val heroiTabLayout = view.findViewById<TabLayout>(R.id.heroi_tab_layout)
        configuraTabLayout(heroiTabLayout, heroiViewPager)

    }

    private fun configuraViewPager(viewPager: ViewPager) {
        val heroiAdapter = HeroiViewPagerAdapter(fragmentManager = childFragmentManager)
        val heroiBiografia = HeroiBiografiaFragment.criar()
        val heroiFilmes = HeroiFilmesFragment.criar()
        heroiAdapter.addFragment(heroiBiografia, "Biografia")
        heroiAdapter.addFragment(heroiFilmes, "Filmes")
        viewPager.adapter = heroiAdapter
    }

    private fun configuraTabLayout(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.setupWithViewPager(viewPager)
    }

}