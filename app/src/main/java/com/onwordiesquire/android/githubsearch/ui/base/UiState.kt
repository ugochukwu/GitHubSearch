package com.onwordiesquire.android.githubsearch.ui.base

data class UiState(val state: State) {
    companion object {
        fun error(errorMsg: String) = UiState(state = State.Error(errorMsg))
        fun loading() = UiState(state = State.Loading)
        fun <T> success(payload: T) = UiState(state = State.Success(payload))
    }
}

sealed class State {
    object Loading : State()
    class Success<T>(val data: T) : State()
    class Empty(val message: String) : State()
    class Error(val message: String) : State()
    object PaginationSuccess : State()
    object PaginationError : State()
}