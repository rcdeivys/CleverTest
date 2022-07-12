package com.rcdeivys.clevertest.models

import com.google.gson.annotations.SerializedName

data class LocationsResponse(
    @SerializedName("info")
    val info: Info? = Info(),
    @SerializedName("results")
    val results: List<Result>? = listOf()
)