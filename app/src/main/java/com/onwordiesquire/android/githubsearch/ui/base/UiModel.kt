package com.onwordiesquire.android.githubsearch.ui.base

data class UiModel(val state: ModelState)

sealed class ModelState {
    class Loading : ModelState()
    class Success<T>(val data: T) : ModelState()
    class Empty : ModelState()
    class Error : ModelState()
    class PaginationSuccess : ModelState()
    class PaginationError : ModelState()
}