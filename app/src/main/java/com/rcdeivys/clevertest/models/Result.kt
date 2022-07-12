package com.rcdeivys.clevertest.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("species")
    val species: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("origin")
    val origin: Origin? = null,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("episode")
    val episode: List<String?>? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("created")
    val created: String? = null,
    @SerializedName("dimension")
    val dimension: String? = null,
    @SerializedName("residents")
    val residents: List<String>? = null
) : Serializable {

    fun getFirstSeen() = if (episode.isNullOrEmpty().not()) {
        episode?.get(0)?.replace("https://rickandmortyapi.com/api/episode/".toRegex(), "")
            .toString()
    } else {
        "Unknown"
    }
}