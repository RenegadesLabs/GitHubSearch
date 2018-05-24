package com.labs.renegades.githubsearch.datasource

import com.labs.renegades.githubsearch.datasource.local.LocalSearchDataSource
import com.labs.renegades.githubsearch.datasource.remote.RemoteSearchDataSource
import com.labs.renegades.githubsearch.datasource.remote.api.Repository
import io.reactivex.Flowable

object SearchRepository : SearchDataSource {

    private val localDataSource = LocalSearchDataSource()
    private val remoteDataSource = RemoteSearchDataSource()

    override fun search(searchQuery: String): Flowable<List<Repository>> {
        return localDataSource.search(searchQuery)
                .mergeWith(remoteDataSource.search(searchQuery)
                        .onErrorReturn { emptyList() }
                        .filter { t -> t.isNotEmpty() }
                        .doOnNext { persist(it, searchQuery) })
                .distinct()
    }

    override fun persist(repos: List<Repository>, searchQuery: String) {
        localDataSource.persist(repos, searchQuery)
    }
}