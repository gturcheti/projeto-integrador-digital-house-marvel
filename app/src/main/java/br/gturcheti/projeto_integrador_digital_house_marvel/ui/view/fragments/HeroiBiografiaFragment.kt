package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentHeroiBiografiaBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class HeroiBiografiaFragment : Fragment(R.layout.fragment_heroi_biografia) {

    lateinit var binding: FragmentHeroiBiografiaBinding

    private val heroi: HeroiVO? by lazy {
        arguments?.getSerializable(HEROI_KEY)?.let { it as HeroiVO }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("HEROI_BIO", "${heroi?.image}")
        setupViews()
    }

    private fun setupViews() {
        binding = FragmentHeroiBiografiaBinding.bind(requireView())
        lifecycleScope.launch {
            heroi.let {
                with(binding) {
                    heroiFragmentNomePersonagem.text = heroi?.id.toString()
                    heroiFragmentNomeHeroi.text = heroi?.name
                    heroiFragmentDescricao.text = heroi?.description
                }
            }
        }


    }

    companion object {
        fun criar(): HeroiBiografiaFragment = HeroiBiografiaFragment()

        private const val HEROI_KEY = "heroi"
        fun buildBundle(heroi: HeroiVO) = bundleOf(HEROI_KEY to heroi)
    }

}