package com.labs.renegades.githubsearch.datasource.remote.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RequestEntity(@Expose @SerializedName("query") val query: String)

