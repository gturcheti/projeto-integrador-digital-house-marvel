package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.HeroiRecyclerItemBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.model.Heroi

class HeroiRecyclerViewAdapter(
    private val context: Context,
    herois: List<Heroi> = emptyList(),
    var heroiItemListener: (heroi: Heroi) -> Unit = {}
) : RecyclerView.Adapter<HeroiRecyclerViewAdapter.ViewHolder>() {

    private val herois = herois.toMutableList()

    inner class ViewHolder(private val binding: HeroiRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var heroi: Heroi

        init {
            itemView.setOnClickListener {
                if (::heroi.isInitialized) {
                    heroiItemListener(heroi)
                }
            }
        }

        fun vincula(heroi: Heroi) {
            this.heroi = heroi
            val nomeHeroi = binding.heroiRecyclerItemNomeHeroi
            nomeHeroi.text = heroi.nomeHeroi
            val nomePersonagem = binding.heroiRecyclerItemNomePersonagem
            nomePersonagem.text = heroi.nomePersonagem
            val imagemHeroi = binding.heroiRecyclerItemImg
            imagemHeroi.setImageResource(heroi.imagem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = HeroiRecyclerItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val heroi = herois[position]
        holder.vincula(heroi)
    }

    override fun getItemCount(): Int = herois.size

    fun atualiza(herois: List<Heroi>) {
        this.herois.clear()
        this.herois.addAll(herois)
        notifyDataSetChanged()
    }
}