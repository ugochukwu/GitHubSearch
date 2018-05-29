package com.onwordiesquire.android.githubsearch.data.datasource

import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun searchForRespositories(@Query("q") searchTerm: String,
                               @Query("page") page: Int,
                               @Query("per_page") pageSize: Int)
            : Single<Response<SearchResponseDto>>
}

sealed class DataSourceResponse<T> {
    class NoInternet<T> : DataSourceResponse<T>()
    data class Success<T>(val payload: T?, val headers: ResponseHeaders) : DataSourceResponse<T>()
    data class Failure<T>(val code: Int) : DataSourceResponse<T>()
}

typealias ResponseHeaders = MutableMap<String, MutableList<String>>

const val BASE_URL = "https://api.github.com/"
const val PAGE_SIZE = 50