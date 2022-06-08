package br.gturcheti.projeto_integrador_digital_house_marvel.data

import br.gturcheti.projeto_integrador_digital_house_marvel.data.dto.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarvelApiRepository {
    private val api = marvelapi
    suspend fun fetchCharactersList(): Response = withContext(Dispatchers.IO){
        api.fetchCharactersList()
    }
}