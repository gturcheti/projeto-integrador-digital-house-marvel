package br.gturcheti.projeto_integrador_digital_house_marvel.data

import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.HashGenerator.toHash
import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.characters.ResponseCharacters
import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.comics.ResponseComics
import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.marvelapi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarvelApiRepository {

    private val api = marvelapi

    suspend fun fetchCharactersListByNameStartsWith(queryName: String): ResponseCharacters =
        withContext(Dispatchers.IO) {
            val ts = System.currentTimeMillis()
            api.fetchCharactersListByNameStartsWith(
                ts = ts,
                hash = toHash(ts),
                limit = "100",
                offset = "0",
                queryName = queryName.lowercase(),
            )
        }

    suspend fun fetchCharactersListbyName(queryName: String): ResponseCharacters =
        withContext(Dispatchers.IO) {
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

    suspend fun fetchComicByCharacterId(id:String) : ResponseComics = withContext(Dispatchers.IO){
        val ts = System.currentTimeMillis()
        api.fetchComicByCharacterID(
            id,
            "100",
            ts,
            toHash(ts)
        )
    }

}