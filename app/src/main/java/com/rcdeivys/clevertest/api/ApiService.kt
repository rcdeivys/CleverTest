package com.rcdeivys.clevertest.api

import com.rcdeivys.clevertest.models.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    suspend fun getCharacter(): Response<CharacterResponse>

}