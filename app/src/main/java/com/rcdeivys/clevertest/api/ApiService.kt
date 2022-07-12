package com.rcdeivys.clevertest.api

import com.rcdeivys.clevertest.models.CharacterResponse
import com.rcdeivys.clevertest.models.EpisodeResponse
import com.rcdeivys.clevertest.models.LocationsResponse
import com.rcdeivys.clevertest.models.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacter(): Response<CharacterResponse>

    @GET("character")
    suspend fun getCharacterPaginated(@Query("page") page: Int): Response<CharacterResponse>

    @GET("episode/{idEpisode}")
    suspend fun getEpisode(@Path("idEpisode") idEpisode: Int): Response<EpisodeResponse>

    @GET("location")
    suspend fun getLocations(): Response<LocationsResponse>

    @GET("location")
    suspend fun getLocationsPaginated(@Query("page") page: Int): Response<LocationsResponse>

    @GET("character/{idCharacter}")
    suspend fun getCharacterById(@Path("idCharacter") idCharacter: String): Response<Result>
}