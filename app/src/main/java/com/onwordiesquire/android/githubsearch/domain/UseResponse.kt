package com.onwordiesquire.android.githubsearch.domain

data class UseCaseResponse(val result: ResultState)
sealed class ResultState {
    class Success<T>(val payload: T) : ResultState()
    class Failure : ResultState()
    class Empty : ResultState()
}