package com.labs.renegades.githubsearch.datasource.local

import com.labs.renegades.githubsearch.App
import com.labs.renegades.githubsearch.datasource.SearchDataSource
import com.labs.renegades.githubsearch.datasource.local.room.RepositoryEntity
import com.labs.renegades.githubsearch.datasource.remote.api.Repository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class LocalSearchDataSource : SearchDataSource {

    override fun search(searchQuery: String): Flowable<List<Repository>> {
        val repos = App.database.repositoryDao().searchByQuery(searchQuery)

        return repos.map { entities ->
            entities.map { entity ->
                Repository(name = entity.name ?: "", description = entity.description ?: "",
                        url = entity.url ?: "", updatedAt = entity.updatedAt ?: "")
            }.toList()
        }.subscribeOn(Schedulers.io())
    }

    override fun persist(repos: List<Repository>, searchQuery: String) {
        App.database.repositoryDao().insertAll(repos.map { repo ->
            RepositoryEntity(searchQuery = searchQuery, name = repo.name,
                    description = repo.description, url = repo.url, updatedAt = repo.updatedAt)
        })
    }
}
