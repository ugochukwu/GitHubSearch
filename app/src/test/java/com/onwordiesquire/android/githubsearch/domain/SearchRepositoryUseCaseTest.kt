package com.onwordiesquire.android.githubsearch.domain

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.onwordiesquire.android.githubsearch.data.datasource.DataSourceResponse
import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto
import com.onwordiesquire.android.githubsearch.data.repository.DataRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.OngoingStubbing
import provideSampleResponse

@RunWith(MockitoJUnitRunner::class)
class SearchRepositoryUseCaseTest {

    private val dataRepository: DataRepository = mock()
    private lateinit var useCase: SearchRepositoryUseCase
    private val stubSearchRequest = whenever(dataRepository.searchForRepository(any(), anyInt(), anyInt()))

    @Before
    fun setup() {
        useCase = SearchRepositoryUseCase(dataRepository)
    }

    @Test
    fun `Successful search request`() {
        givenSuccessResponse<DataSourceResponse<SearchResponseDto>>(stubSearchRequest, provideSampleResponse())
        useCase.fetchResults("kotlin", 0)
                .test()
                .apply {
                    assertNoErrors()
                    assertValue { useCaseResponse -> useCaseResponse.result is ResultState.Success<*> }
                }
    }


    fun <T> givenSuccessResponse(ongoingStubbing: OngoingStubbing<Single<DataSourceResponse<SearchResponseDto>>>, response: DataSourceResponse.Success<SearchResponseDto>) =
            ongoingStubbing.thenReturn(Single.just(response))

}
