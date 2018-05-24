package com.onwordiesquire.android.githubsearch.data.repository

import com.onwordiesquire.android.githubsearch.data.datasource.DataSourceResponse
import com.onwordiesquire.android.githubsearch.data.datasource.PAGE_SIZE
import com.onwordiesquire.android.githubsearch.data.datasource.RemoteDataSource
import com.onwordiesquire.android.githubsearch.data.dto.SearchResponseDto
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    fun searchForRepository(searchTerm: String, pageSize: Int = PAGE_SIZE, pageNo: Int): Single<DataSourceResponse<SearchResponseDto>> =
            remoteDataSource.searchForRepositories(searchTerm, pageNo, pageSize).map { response ->
                with(response) {
                    when {
                        isSuccessful -> DataSourceResponse.Success(payload = body(), headers = headers().toMultimap())
                        else -> DataSourceResponse.Failure<SearchResponseDto>(code = code())
                    }
                }
            }
}