package com.labs.renegades.githubsearch.datasource.remote.api

import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface Search {

    @POST("graphql")
    fun searchRepos(@Body graph: RequestEntity): Flowable<ResponseEntity>
}