package com.onwordiesquire.android.githubsearch.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.onwordiesquire.android.githubsearch.domain.RepoPage
import com.onwordiesquire.android.githubsearch.domain.ResultState
import com.onwordiesquire.android.githubsearch.domain.SearchRepositoryUseCase
import com.onwordiesquire.android.githubsearch.ui.base.State
import com.onwordiesquire.android.githubsearch.ui.base.UiState
import com.onwordiesquire.android.githubsearch.utils.MyLogger
import com.onwordiesquire.android.githubsearch.utils.executeAsync
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchRepositoryUseCase: SearchRepositoryUseCase)
    : ViewModel(), MyLogger {

    private var internalState: InternalViewModelState = InternalViewModelState()
    private val uiLiveData: MutableLiveData<UiState> = MutableLiveData()
    private var job: Job? = null

    fun subscribeToUiEvents(): LiveData<UiState> = uiLiveData

    fun onSearchForRepository(searchTerm: String) {
        internalState = internalState.copy(searchTerm = searchTerm)

        job = launch(UI) {
            try {
                udpateUi(UiState.loading())
                val result = searchForRepo(searchTerm)
                udpateUi(mapResultToUiState(result))
            } catch (e: Exception) {
                udpateUi(UiState.error(ERROR_MSG))
            }
        }
    }

    private suspend inline fun searchForRepo(searchTerm: String): ResultState {
        return executeAsync {
            searchRepositoryUseCase.searchForRepositories(searchTerm, internalState.pageNo)
        }.result
    }

    fun onLoadMore() {
        internalState = internalState.copy(pageNo = internalState.pageNo.inc())
    }

    private fun udpateUi(uiState: UiState) {
        uiLiveData.value = uiState
    }

    override fun onCleared() {
        job?.cancel()
    }

    private fun mapResultToUiState(result: ResultState): UiState {
        return when (result) {
            is ResultState.Success<*> -> (result.payload as RepoPage).run {
                internalState = internalState.copy(cachedRepo = this)
                UiState(state = State.Success(this))
            }
            is ResultState.Failure -> UiState(state = State.Error(ERROR_MSG))
            is ResultState.Empty -> UiState(state = State.Empty(EMPTY_MSG))
        }
    }


    data class InternalViewModelState(val pageNo: Int = 0,
                                      val searchTerm: String = "",
                                      val cachedRepo: RepoPage? = null)

}

const val ERROR_MSG = "Sorry something went wrong, please try again"
const val EMPTY_MSG = "Sorry we couldn't find anything"

//operator fun SearchRepositoryUseCase.getValue(searchViewModel: SearchViewModel, property: KProperty<*>): Any {
//    searchForRepositories()
//}