package com.labs.renegades.githubsearch.domain

import io.reactivex.disposables.Disposable

interface UseCase<V : UseCase.Request> {

    fun execute(request: V): Disposable

    interface Request
}