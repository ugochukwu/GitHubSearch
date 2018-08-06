package com.onwordiesquire.android.githubsearch.data.repository

import com.onwordiesquire.android.githubsearch.data.datasource.DataSourceResponse
import com.onwordiesquire.android.githubsearch.data.datasource.GithubApi
import com.onwordiesquire.android.githubsearch.data.datasource.PAGE_SIZE
import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto
import kotlinx.coroutines.Deferred
import javax.inject.Inject

interface DataRepository {
    suspend fun searchForRepository(searchTerm: String, pageSize: Int = PAGE_SIZE, pageNo: Int): DataSourceResponse<SearchResponseDto>
}

class DataRepositoryImpl @Inject constructor(private val githubApi: GithubApi) : DataRepository {

    override suspend fun searchForRepository(searchTerm: String, pageSize: Int, pageNo: Int): DataSourceResponse<SearchResponseDto> {
        val response = loadAsync(githubApi.searchForRespositories(searchTerm, pageNo, pageSize))
        return with(response) {
            when {
                isSuccessful -> DataSourceResponse.Success(payload = body(), headers = headers().toMultimap())
                else -> DataSourceResponse.Failure(code = code())
            }
        }
    }
}

private suspend fun <T> loadAsync(deferred: Deferred<T>): T = deferred.await()