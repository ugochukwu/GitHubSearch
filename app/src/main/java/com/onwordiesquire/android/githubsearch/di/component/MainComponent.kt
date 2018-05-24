package com.onwordiesquire.android.githubsearch.di.component

import com.onwordiesquire.android.githubsearch.GithubSearchApp
import com.onwordiesquire.android.githubsearch.di.modules.BuildersModule
import com.onwordiesquire.android.githubsearch.di.modules.GitHubApiModule
import com.onwordiesquire.android.githubsearch.di.modules.NetworkModule
import com.onwordiesquire.android.githubsearch.di.modules.ViewModelFactoryModule
import com.onwordiesquire.android.githubsearch.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class,
    GitHubApiModule::class,
    ViewModelFactoryModule::class,
    BuildersModule::class,
    ViewModelModule::class,
    AndroidSupportInjectionModule::class])
interface MainComponent {
    fun inject(githubSearchApp: GithubSearchApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: GithubSearchApp): Builder

        fun build(): MainComponent
    }
}