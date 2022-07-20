package br.gturcheti.projeto_integrador_digital_house_marvel.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.RecyclerItemComicBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.tryToLoadImage
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.ComicVO

class ComicRecyclerViewAdapter(
    private val onItemClicked: (comic: ComicVO) -> Unit
) : RecyclerView.Adapter<ComicRecyclerViewAdapter.ViewHolder>() {

    private val comics: MutableList<ComicVO> = mutableListOf()

    inner class ViewHolder(
        private val binding: RecyclerItemComicBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comic: ComicVO) {
            with(binding) {
                comicItemTitle.text = comic.title
                comicItemImg.tryToLoadImage(comic.image)
                itemView.setOnClickListener { onItemClicked.invoke(comic) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemComicBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(comics[position])

    override fun getItemCount(): Int = comics.size

    fun updateComics(comics: List<ComicVO>) {
        this.comics.clear()
        this.comics.addAll(comics)
        notifyDataSetChanged()
    }
}