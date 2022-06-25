package br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.HeroiRecyclerItemBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.model.Heroi
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO
import com.squareup.picasso.Picasso

class HeroiRecyclerViewAdapter(
//    private val context: Context,
//    herois: List<Heroi> = emptyList(),
    var context: Context,
    var heroiItemListener: (heroi: Heroi) -> Unit = {}
) : RecyclerView.Adapter<HeroiRecyclerViewAdapter.ViewHolder>() {

    private val herois: MutableList<HeroiVO> = mutableListOf()

    inner class ViewHolder(
        private val binding: HeroiRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var heroi: Heroi

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
            Picasso.with(context)
                .load(heroi.image)
                .into(imageView)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HeroiRecyclerItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(herois[position])

    override fun getItemCount(): Int = herois.size

    fun atualiza(herois: List<HeroiVO>) {
        this.herois.clear()
        this.herois.addAll(herois)
        notifyDataSetChanged()
    }
}