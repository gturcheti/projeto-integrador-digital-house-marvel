package br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.RecyclerItemHeroiBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.tryToLoadImage
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO

class HeroiRecyclerViewAdapter(
    private val onItemClicked: (heroi: HeroiVO) -> Unit
) : RecyclerView.Adapter<HeroiRecyclerViewAdapter.ViewHolder>() {

    private val herois: MutableList<HeroiVO> = mutableListOf()

    inner class ViewHolder(
        private val binding: RecyclerItemHeroiBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: HeroiVO) {
            with(binding) {
                heroiRecyclerItemNomeHeroi.text = character.name
                heroiRecyclerItemNomePersonagem.text = character.description
                heroiRecyclerItemImg.tryToLoadImage(character.image)
                itemView.setOnClickListener { onItemClicked.invoke(character) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemHeroiBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(herois[position])

    override fun getItemCount(): Int = herois.size

    fun atualizaHerois(herois: List<HeroiVO>) {
        this.herois.clear()
        this.herois.addAll(herois)
        notifyDataSetChanged()
    }
}