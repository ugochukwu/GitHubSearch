package com.onwordiesquire.android.githubsearch.data.repository

import com.onwordiesquire.android.githubsearch.data.datasource.DataSourceResponse
import com.onwordiesquire.android.githubsearch.data.datasource.GithubApi
import com.onwordiesquire.android.githubsearch.data.datasource.PAGE_SIZE
import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto
import io.reactivex.Single
import javax.inject.Inject

interface DataRepository {
    fun searchForRepository(searchTerm: String, pageSize: Int = PAGE_SIZE, pageNo: Int): Single<DataSourceResponse<SearchResponseDto>>
}

class DataRepositoryImpl @Inject constructor(private val githubApi: GithubApi) : DataRepository {

    override fun searchForRepository(searchTerm: String, pageSize: Int, pageNo: Int): Single<DataSourceResponse<SearchResponseDto>> =
            githubApi.searchForRespositories(searchTerm, pageNo, pageSize).map { response ->
                with(response) {
                    when {
                        isSuccessful -> DataSourceResponse.Success(payload = body(), headers = headers().toMultimap())
                        else -> DataSourceResponse.Failure<SearchResponseDto>(code = code())
                    }
                }
            }
}