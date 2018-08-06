package com.onwordiesquire.android.githubsearch.ui.search

import SearchTestDataFactory.Companion.provideSampleDataSourceResponse
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.onwordiesquire.android.githubsearch.domain.SearchRepositoryUseCase
import com.onwordiesquire.android.githubsearch.domain.UseCaseResponse
import com.onwordiesquire.android.githubsearch.ui.base.ModelState
import com.onwordiesquire.android.githubsearch.ui.base.UiState
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.OngoingStubbing
import utils.RxSchedulersOverride
import utils.TestLiveDataWrapper
import utils.testLiveDataWrapper
import kotlin.reflect.KClass

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulersExecutorRule = RxSchedulersOverride()

    private val searchRepositoryUseCase: SearchRepositoryUseCase = mock()
    private lateinit var viewModel: SearchViewModel
    private val stubUseCaseRequest = whenever(searchRepositoryUseCase.searchForRepositories(any(), any()))

    @Before
    fun setup() {
        viewModel = SearchViewModel(searchRepositoryUseCase)
    }

    @Test
    fun onSearchForRepository() {
        val sampleResponse = provideSampleDataSourceResponse<UseCaseResponse>()
        val testObserver = obtainTestObserver()

        givenSuccesfulSearchRequest(sampleResponse)

        viewModel.onSearchForRepository("kotlin")

        assertObservedStates(testObserver, ModelState.Loading::class, ModelState.Success::class)
    }

    private fun assertObservedStates(testObserver: TestLiveDataWrapper<UiState>, loadingState: KClass<ModelState.Loading>, SuccessState: KClass<ModelState.Success<*>>) {
        testObserver.observedValues.map { it?.state }.forEachIndexed { index, state ->
            Truth.assert_().that(state).isInstanceOf(when (index) {
                0 -> loadingState.java
                else -> SuccessState.java
            })
        }
    }

    private fun obtainTestObserver() = viewModel.subscribeToUiEvents().testLiveDataWrapper()

    private fun givenSuccesfulSearchRequest(sampleResponse: UseCaseResponse) {
        givenSuccessResponse(stubUseCaseRequest, sampleResponse)
    }

    private fun givenSuccessResponse(stubUseCaseRequest: OngoingStubbing<Single<UseCaseResponse>>, response: UseCaseResponse) =
            stubUseCaseRequest.thenReturn(Single.just(response))

}