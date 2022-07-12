package com.rcdeivys.clevertest.models


import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("results")
    val results: List<Result>? = null
)