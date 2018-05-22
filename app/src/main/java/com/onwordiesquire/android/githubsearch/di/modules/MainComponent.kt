package com.onwordiesquire.android.githubsearch.di

import com.onwordiesquire.android.githubsearch.GithubSearchApp
import com.onwordiesquire.android.githubsearch.di.modules.GitHubApiModule
import com.onwordiesquire.android.githubsearch.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, GitHubApiModule::class])
interface MainComponent {
    fun inject(githubSearchApp: GithubSearchApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: GithubSearchApp): Builder

        fun build(): MainComponent
    }
}