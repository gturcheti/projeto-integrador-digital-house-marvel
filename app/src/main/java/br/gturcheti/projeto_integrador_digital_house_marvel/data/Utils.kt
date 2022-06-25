package br.gturcheti.projeto_integrador_digital_house_marvel.data

import br.gturcheti.projeto_integrador_digital_house_marvel.BuildConfig
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toMD5

object HashGenerator {

    fun toHash(ts: Long): String {
        val value: String = ts.toString() + BuildConfig.MARVEL_API_PRIVATEKEY + BuildConfig.MARVEL_API_PUBLICKEY
        return value.toMD5()
    }
}