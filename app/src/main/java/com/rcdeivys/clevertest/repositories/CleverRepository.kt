package com.rcdeivys.clevertest.repositories

import com.rcdeivys.clevertest.api.ApiService
import com.rcdeivys.clevertest.models.CharacterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class CleverRepository constructor(
    private val apiService: ApiService
) {

    suspend fun getCharacter(): Flow<Response<CharacterResponse>> = flow {
        emit(apiService.getCharacter())
    }
}