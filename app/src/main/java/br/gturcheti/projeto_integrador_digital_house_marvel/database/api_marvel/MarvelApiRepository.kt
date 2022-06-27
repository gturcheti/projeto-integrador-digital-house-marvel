package br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel

import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.HashGenerator.toHash
import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.dto.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarvelApiRepository {

    private val api = marvelapi

    suspend fun fetchCharactersList(limit: String, offset:String): Response = withContext(Dispatchers.IO) {
        val ts = System.currentTimeMillis()
        api.fetchCharactersList(
            ts,
            toHash(ts),
            limit,
            offset,
        )
    }

    suspend fun fetchCharactersById(id:String): Response = withContext(Dispatchers.IO) {
        val ts = System.currentTimeMillis()
        api.fetchCharactersById(
            id,
            ts,
            toHash(ts),
        )
    }
}