package com.labs.renegades.githubsearch.search

import android.arch.lifecycle.MutableLiveData
import com.labs.renegades.githubsearch.datasource.remote.api.Repository
import com.labs.renegades.githubsearch.domain.usecase.SearchUseCase
import com.labs.renegades.githubsearch.mvvm.Resource
import com.labs.renegades.githubsearch.mvvm.BaseViewModel

class SearchViewModel : BaseViewModel() {

    private val reposLiveData = MutableLiveData<Resource<List<Repository>>>()
    private val searchUseCase = SearchUseCase()

    override fun onStart() {
        super.onStart()

        searchUseCase.subscribe(
                onNext = { reposLiveData.value = Resource.success(it) },
                onError = { reposLiveData.value = Resource.error(it.message ?: "", null) })
    }

    fun searchRepos(searchQuery: String) {
        val request = SearchUseCase.Request(searchQuery)
        disposables?.add(searchUseCase.execute(request))
    }

    fun getReposLiveData(): MutableLiveData<Resource<List<Repository>>> {
        return reposLiveData
    }
}