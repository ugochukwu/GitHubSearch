package com.onwordiesquire.android.githubsearch.data.datasource

import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface GithubApi {

    @GET("search/repositories")
    fun searchForRespositories(@Query("q") searchTerm: String,
                               @Query("per_page") pageSize: Int,
                               @Query("page") page: Int)
            : Single<Response<SearchResponseDto>>
}

class RemoteDataSource @Inject constructor(private val githubApi: GithubApi) {

    fun searchForRepositories(searchTerm: String, pageNo: Int = 0, pageSize: Int = PAGE_SIZE)
            : Single<Response<SearchResponseDto>> = githubApi.searchForRespositories(searchTerm, pageSize, pageNo)
}

const val BASE_URL = "https://api.github.com/"
const val PAGE_SIZE = 10
