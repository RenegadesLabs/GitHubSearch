package com.labs.renegades.githubsearch

import com.labs.renegades.githubsearch.datasource.SearchDataSource
import com.labs.renegades.githubsearch.datasource.remote.api.Repository
import com.labs.renegades.githubsearch.domain.usecase.SearchUseCase
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Rule
import org.junit.Test


class SearchUseCaseTest {

    @Rule
    @JvmField
    val schedulers = RxImmediateSchedulerRule()

    @Test
    fun `get repos list`() {
        //given
        val mockedRepo = mock<SearchDataSource>()
        whenever(mockedRepo.search("java")).thenReturn(Flowable.just(arrayOf(
                Repository(name = "java", description = "desc", url = "url", updatedAt = "upd"),
                Repository(name = "kotlin", description = "desc", url = "url", updatedAt = "upd")
        ).asList()))

        val objectUnderTest = SearchUseCase(mockedRepo)

        var result: List<Repository> = ArrayList()
        var error: Throwable? = null
        objectUnderTest.subscribe(
                onNext = { result = it },
                onError = { error = it })

        //when
        objectUnderTest.execute(SearchUseCase.Request("java"))

        //then
        assert(result.size == 2)
        assert(result[0].name == "java")
        assert(result[1].name == "kotlin")
        assert(error == null)
    }
}