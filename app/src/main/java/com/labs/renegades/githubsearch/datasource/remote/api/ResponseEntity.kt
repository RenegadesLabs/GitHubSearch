package com.labs.renegades.githubsearch.datasource.remote.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseEntity(@Expose @SerializedName("data") val data: Data)

data class Data(@Expose @SerializedName("search") val search: SearchEntity)

data class SearchEntity(@Expose @SerializedName("nodes") val nodes: Array<Repository>)

data class Repository(@Expose @SerializedName("name") val name: String,
                      @Expose @SerializedName("description") val description: String,
                      @Expose @SerializedName("url") val url: String,
                      @Expose @SerializedName("updatedAt") val updatedAt: String)
