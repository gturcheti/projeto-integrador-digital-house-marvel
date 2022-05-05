package br.gturcheti.projeto_integrador_digital_house_marvel.model

class HeroiDao {

    companion object {
        private val herois = mutableListOf<Heroi>()
    }

    fun add (heroi:Heroi) {
        herois.add(heroi)
    }

    fun buscaTodos() : List<Heroi> {
        return herois.toList()
    }
}