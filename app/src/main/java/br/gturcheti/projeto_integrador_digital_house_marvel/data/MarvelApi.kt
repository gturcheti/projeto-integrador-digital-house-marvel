package br.gturcheti.projeto_integrador_digital_house_marvel.data

import br.gturcheti.projeto_integrador_digital_house_marvel.BuildConfig
import br.gturcheti.projeto_integrador_digital_house_marvel.data.dto.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MarvelApi {

    @GET("v1/public/characters?ts=${BuildConfig.MARVEL_API_TS}&apikey=${BuildConfig.MARVEL_API_PUBLICKEY}" +
            "&hash=${BuildConfig.MARVEL_API_HASH}")
    suspend fun fetchCharactersList ()
        : Response

}


/*
interface MarvelApi {

    @GET("v1/public/characters?")
    suspend fun getComicsFromCharacter (
        @Query("apikey") apikey: String = BuildConfig.MARVEL_API_PUBLICKEY,
        @Query("ts") ts: Long,
        @Query("hash") hash: String): Response

}*/