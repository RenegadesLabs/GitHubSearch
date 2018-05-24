package com.labs.renegades.githubsearch.datasource.remote

import com.labs.renegades.githubsearch.App
import com.labs.renegades.githubsearch.datasource.SearchDataSource
import com.labs.renegades.githubsearch.datasource.remote.api.Repository
import com.labs.renegades.githubsearch.datasource.remote.api.RequestEntity
import com.labs.renegades.githubsearch.datasource.remote.api.Search
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers


class RemoteSearchDataSource : SearchDataSource {

    private val api = App.retrofit!!.create(Search::class.java)

    override fun search(searchQuery: String): Flowable<List<Repository>> {
        return api.searchRepos(RequestEntity(
                "{ search(query: \"" + searchQuery + "\", first: 10, type: REPOSITORY)" +
                        "{nodes{ ... on Repository{name description url updatedAt}}}}"))
                .flatMap { response -> Flowable.just(response.data.search.nodes.asList()) }
                .subscribeOn(Schedulers.io())
    }

    override fun persist(repos: List<Repository>, searchQuery: String) {}
}


// GraphQL
//
// {
//  search (query: "searchQuery", first: 10, type: REPOSITORY) {
//      nodes {
//          ... on Repository {
//              name
//              description
//              url
//              updatedAt
//          }
//      }
//  }
// }