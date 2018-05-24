package com.labs.renegades.githubsearch.datasource

import com.labs.renegades.githubsearch.datasource.remote.api.Repository
import io.reactivex.Flowable

interface SearchDataSource {

    fun search(searchQuery: String): Flowable<List<Repository>>

    fun persist(repos: List<Repository>, searchQuery: String)
}