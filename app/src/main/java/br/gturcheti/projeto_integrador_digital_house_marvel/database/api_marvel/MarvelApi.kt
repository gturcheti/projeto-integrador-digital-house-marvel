package br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel



import br.gturcheti.projeto_integrador_digital_house_marvel.BuildConfig
import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.dto.characters.ResponseCharacters
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelApi {

    //CHARACTERS GETTERS

    @GET("v1/public/characters?apikey=${BuildConfig.MARVEL_API_PUBLICKEY}")
    suspend fun fetchCharactersList (
        @Query("ts") ts :Long,
        @Query("hash") hash: String,
        @Query("limit") limit: String,
        @Query("offset") offset:String
    ) : ResponseCharacters

    @GET("v1/public/characters?apikey=${BuildConfig.MARVEL_API_PUBLICKEY}")
    suspend fun fetchCharactersListByNameStartsWith (
        @Query("ts") ts :Long,
        @Query("hash") hash: String,
        @Query("limit") limit: String,
        @Query("offset") offset:String,
        @Query("nameStartsWith") queryName :String,
    ) : ResponseCharacters

    @GET("v1/public/characters?apikey=${BuildConfig.MARVEL_API_PUBLICKEY}")
    suspend fun fetchCharactersNameListByName (
        @Query("ts") ts :Long,
        @Query("hash") hash: String,
        @Query("limit") limit: String,
        @Query("offset") offset:String,
        @Query("name") queryName :String,
    ) : ResponseCharacters

    @GET("v1/public/characters/{id}?apikey=${BuildConfig.MARVEL_API_PUBLICKEY}")
    suspend fun fetchCharactersById (
        @Path("id") id: String,
        @Query("ts") ts :Long,
        @Query("hash") hash: String,
    ) : ResponseCharacters



}
