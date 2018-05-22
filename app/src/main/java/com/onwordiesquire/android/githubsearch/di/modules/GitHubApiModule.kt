package com.onwordiesquire.android.githubsearch.di.modules

import com.onwordiesquire.android.githubsearch.data.datasource.GithubApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class GitHubApiModule {

    @Provides
    fun providApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)
}