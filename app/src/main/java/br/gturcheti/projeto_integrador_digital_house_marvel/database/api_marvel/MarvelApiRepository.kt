package br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel

import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.HashGenerator.toHash
import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.dto.characters.ResponseCharacters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarvelApiRepository {

    private val api = marvelapi

    suspend fun fetchCharactersList(): ResponseCharacters = withContext(Dispatchers.IO) {
        val ts = System.currentTimeMillis()
        api.fetchCharactersList(
            ts,
            toHash(ts),
            limit = "100",
            offset = "0",
        )
    }

    suspend fun fetchCharactersListByNameStartsWith(queryName: String): ResponseCharacters = withContext(Dispatchers.IO) {
        val ts = System.currentTimeMillis()
        api.fetchCharactersListByNameStartsWith(
            ts = ts,
            hash = toHash(ts),
            limit = "100",
            offset = "0",
            queryName = queryName.lowercase(),
        )
    }

    suspend fun fetchCharactersListbyName(queryName: String): ResponseCharacters = withContext(Dispatchers.IO) {
        val ts = System.currentTimeMillis()
        api.fetchCharactersNameListByName(
            ts = ts,
            hash = toHash(ts),
            limit = "100",
            offset = "0",
            queryName = queryName.lowercase(),
        )
    }

    suspend fun fetchCharactersById(id: String): ResponseCharacters = withContext(Dispatchers.IO) {
        val ts = System.currentTimeMillis()
        api.fetchCharactersById(
            id,
            ts,
            toHash(ts),
        )
    }
}