package br.gturcheti.projeto_integrador_digital_house_marvel.extensions

import android.widget.ImageView
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import coil.load

fun ImageView.tryToLoadImage(
    url: String? = null,
    fallback: Int = R.drawable.imagem_padrao
) {
    load(url) {
        fallback(fallback)
        error(R.drawable.imagem_erro)
        placeholder(R.drawable.placeholder)
    }
}