package br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.RecyclerItemHeroiBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO

class HeroiRecyclerViewAdapter(
//    private val context: Context,
//    herois: List<Heroi> = emptyList(),
    var heroiItemListener: (heroi: HeroiVO) -> Unit = {}
) : RecyclerView.Adapter<HeroiRecyclerViewAdapter.ViewHolder>() {

    private val herois: MutableList<HeroiVO> = mutableListOf()

    inner class ViewHolder(
        private val binding: RecyclerItemHeroiBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var heroi: HeroiVO

        init {
            itemView.setOnClickListener {
                if (::heroi.isInitialized) {
                    heroiItemListener(heroi)
                }
            }
        }

        fun bind (heroi: HeroiVO) {
            binding.heroiRecyclerItemNomeHeroi.text = heroi.name
            val imageView : ImageView = itemView.findViewById(R.id.heroi_recycler_item_img)
            Log.i("HEROY_RECYCLER_ITEM", "IMG_URL = ${heroi.image}")
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