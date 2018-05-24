package com.labs.renegades.githubsearch.domain.usecase

import com.labs.renegades.githubsearch.datasource.SearchDataSource
import com.labs.renegades.githubsearch.datasource.SearchRepository
import com.labs.renegades.githubsearch.datasource.remote.api.Repository
import com.labs.renegades.githubsearch.domain.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

open class SearchUseCase() : UseCase<SearchUseCase.Request> {

    constructor(repository: SearchDataSource) : this() {
        this.repository = repository
    }

    private val subject: PublishSubject<List<Repository>> = PublishSubject.create()
    var repository: SearchDataSource? = null

    override fun execute(request: Request): Disposable {

        return (repository ?: SearchRepository).search(request.searchString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> subject.onNext(response) },
                        { throwable -> subject.onError(throwable) })
    }

    fun subscribe(onNext: (List<Repository>) -> Unit, onError: (Throwable) -> Unit) {
        subject.subscribe(onNext, onError)
    }

    class Request(val searchString: String) : UseCase.Request
}