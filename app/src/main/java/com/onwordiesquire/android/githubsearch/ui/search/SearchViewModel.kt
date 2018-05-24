package com.onwordiesquire.android.githubsearch.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.onwordiesquire.android.githubsearch.domain.RepoPage
import com.onwordiesquire.android.githubsearch.domain.RepositoryList
import com.onwordiesquire.android.githubsearch.domain.ResultState
import com.onwordiesquire.android.githubsearch.domain.SearchRepositoryUseCase
import com.onwordiesquire.android.githubsearch.domain.UseCaseResponse
import com.onwordiesquire.android.githubsearch.ui.base.ModelState
import com.onwordiesquire.android.githubsearch.ui.base.UiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchRepositoryUseCase: SearchRepositoryUseCase)
    : ViewModel() {

    private lateinit var internalState: InternalViewModelState
    private val uiModelLiveData: MutableLiveData<UiModel> = MutableLiveData()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
        get() = if (field.isDisposed) CompositeDisposable() else field

    fun subscribeToUiEvents(): LiveData<UiModel> = uiModelLiveData

    fun init() {
        internalState = InternalViewModelState()
    }


    fun onSearchForRepository(searchTerm: String) {
        internalState = internalState.copy(searchTerm = searchTerm)
        compositeDisposable.add(searchRepositoryUseCase.fetchResults(searchTerm, internalState.pageNo)
                .toFlowable()
                .map({ mapResultToUiState(it) })
                .onErrorReturnItem(UiModel(state = ModelState.Error()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(UiModel(state = ModelState.Loading()))
                .subscribe({
                    uiModelLiveData.value = it
                }))
    }

    fun onLoadMore() {
        internalState = internalState.copy(pageNo = internalState.pageNo.inc())
        searchRepositoryUseCase
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun mapResultToUiState(useCaseResponse: UseCaseResponse): UiModel {
        return with(useCaseResponse) {
            when (result) {
                is ResultState.Success<*> -> UiModel(state = ModelState.Success(result.payload as RepoPage))
                        .also { internalState = internalState.copy(cachedList = result.payload.repositoryList) }
                is ResultState.Failure -> UiModel(state = ModelState.Error())
                is ResultState.Empty -> UiModel(state = ModelState.Empty())
            }
        }
    }

    private data class InternalViewModelState(val pageNo: Int = 0,
                                              val searchTerm: String = "",
                                              val cachedList: RepositoryList = emptyList())
}

data class ViewModelConfiguration(val pageNo: Int = 0)
