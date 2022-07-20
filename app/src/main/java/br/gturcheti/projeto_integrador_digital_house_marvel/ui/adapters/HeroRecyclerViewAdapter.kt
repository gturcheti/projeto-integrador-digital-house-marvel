package br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.RecyclerItemHeroBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.tryToLoadImage
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroVO

class HeroRecyclerViewAdapter(
    private val onItemClicked: (heroi: HeroVO) -> Unit
) : RecyclerView.Adapter<HeroRecyclerViewAdapter.ViewHolder>() {

    private val herois: MutableList<HeroVO> = mutableListOf()

    inner class ViewHolder(
        private val binding: RecyclerItemHeroBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: HeroVO) {
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
        val binding = RecyclerItemHeroBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(herois[position])

    override fun getItemCount(): Int = herois.size

    fun atualizaHerois(herois: List<HeroVO>) {
        this.herois.clear()
        this.herois.addAll(herois)
        notifyDataSetChanged()
    }
}