package com.onwordiesquire.android.githubsearch.di.modules

import com.onwordiesquire.android.githubsearch.data.repository.DataRepository
import com.onwordiesquire.android.githubsearch.data.repository.DataRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideDataRepository(repositoryImpl: DataRepositoryImpl): DataRepository
}