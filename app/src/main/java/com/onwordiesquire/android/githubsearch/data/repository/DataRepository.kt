package com.onwordiesquire.android.githubsearch.data.repository

import com.onwordiesquire.android.githubsearch.data.datasource.PAGE_SIZE
import com.onwordiesquire.android.githubsearch.data.datasource.RemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    fun searchForRepository(searchTerm: String, pageSize: Int = PAGE_SIZE, pageNo: Int) =
            remoteDataSource.searchForRepositories(searchTerm, pageNo, pageSize)
}