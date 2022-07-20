package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroiRecyclerViewBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import br.gturcheti.projeto_integrador_digital_house_marvel.data.preferences.characterDataStore
import br.gturcheti.projeto_integrador_digital_house_marvel.data.preferences.characterIdPreference
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters.HeroRecyclerViewAdapter
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.HeroListViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.Result
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroVO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HeroListFragment : Fragment(R.layout.fragment_heroi_recycler_view) {

    private lateinit var binding: FragmentHeroiRecyclerViewBinding
    private val viewModel by viewModels<HeroListViewModel>()
    private val heroiAdapter = HeroRecyclerViewAdapter(::onItemClicked)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding = FragmentHeroiRecyclerViewBinding.bind(requireView())
        with(binding) {

            rvHeroList.adapter = heroiAdapter

            viewModel.fetchLocalFavorites(requireContext())

            heroListSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                private var job: Job? = null

                override fun onQueryTextSubmit(queryName: String?): Boolean {
                    heroListSearchView.clearFocus()
                    queryName.isNullOrEmpty().let { boo ->
                        if (boo) viewModel.fetchLocalFavorites(requireContext())
                        else viewModel.fetchHeroListOnQueryTextSubmit(queryName)
                    }
                    return false
                }

                override fun onQueryTextChange(queryName: String?): Boolean {
                    job?.cancel()
                    queryName.isNullOrEmpty().let { boo ->
                        job = lifecycleScope.launch {
                            delay(750)
                            if (boo) viewModel.fetchLocalFavorites(requireContext())
                            else viewModel.fetchHeroListOnQueryTextChange(queryName)
                        }
                    }
                    return false
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.heroItem.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading()
                is Result.Error -> showError()
                is Result.Success -> showContent(it.data)
            }
        }
    }

    private fun showContent(herois: List<HeroVO>) {
        updateLoadingView(false)
        heroiAdapter.atualizaHerois(herois)
    }


    fun onItemClicked(character: HeroVO) {
        character.let {
            lifecycleScope.launch {
                requireContext().characterDataStore.edit { preferences ->
                    preferences[characterIdPreference] = character.id
                    Log.i("PREFERENCES_SEND", "onItemClicked: ${preferences}")
                }
                findNavController().navigate(R.id.action_heroiRV_to_heroiVP)
            }
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
        binding.heroListProgressbar.isVisible = isLoadingVisible
    }

}