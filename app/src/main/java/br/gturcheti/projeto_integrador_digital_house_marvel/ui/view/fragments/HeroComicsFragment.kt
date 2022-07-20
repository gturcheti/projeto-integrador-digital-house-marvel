package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroComicsBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters.ComicRecyclerViewAdapter
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.ComicViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.Result
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.ComicVO

class HeroComicsFragment : Fragment(R.layout.fragment_hero_comics) {

    private lateinit var binding: FragmentHeroComicsBinding
    private val viewModel by viewModels<ComicViewModel>()
    private val comicAdapter = ComicRecyclerViewAdapter(::onItemClicked)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }


    private fun setupViews() {
        binding = FragmentHeroComicsBinding.bind(requireView())
        with(binding) {
            rvComicList.adapter = comicAdapter
            rvComicList.layoutManager = GridLayoutManager(requireContext(), 2)
            viewModel.fetchComicByCharacterID(requireContext())
        }

    }

    fun onItemClicked(comic: ComicVO) {
        comic.let {

        }
    }

    private fun setupObservers() {
        viewModel.comicItem.observe(viewLifecycleOwner){
            when (it) {
                is Result.Loading -> showLoading()
                is Result.Success -> showContent(it.data)
                is Result.Error -> showError()
            }
        }
    }

    private fun showError() {
        updateLoadingView(false)
        updateErrorView(true)
    }

    private fun showContent(comics: List<ComicVO>) {
        updateLoadingView(false)
        updateErrorView(false)
        comicAdapter.updateComics(comics)
    }

    private fun showLoading() {
        updateLoadingView(true)
        updateErrorView(false)
    }

    private fun updateLoadingView(isLoadingVisible: Boolean) {
        binding.comicListProgressbar.isVisible = isLoadingVisible
    }

    private fun updateErrorView(isErrorVisible: Boolean) {
        binding.comicWarningTv.isVisible = isErrorVisible
    }

    companion object {
        fun criar(): HeroComicsFragment = HeroComicsFragment()
    }
}