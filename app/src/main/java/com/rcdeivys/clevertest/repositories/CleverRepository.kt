package com.rcdeivys.clevertest.repositories

import com.rcdeivys.clevertest.api.ApiService
import com.rcdeivys.clevertest.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class CleverRepository constructor(
    private val apiService: ApiService
) {

    suspend fun getCharacter() = flow {
        emit(apiService.getCharacter())
    }

    suspend fun getCharacterPaginated(page: Int) = flow {
        emit(apiService.getCharacterPaginated(page))
    }

    suspend fun getEpisode(idEpisode: Int = 0) = flow {
        emit(apiService.getEpisode(idEpisode))
    }

    suspend fun getLocations() = flow {
        emit(apiService.getLocations())
    }

    suspend fun getLocationsPaginated(page: Int) = flow {
        emit(apiService.getLocationsPaginated(page))
    }

    fun getCharacterById(idResident: String): Flow<Response<Result>> = flow {
        emit(apiService.getCharacterById(idResident))
    }
}