package com.onwordiesquire.android.githubsearch.di.modules

import com.onwordiesquire.android.githubsearch.MainActivity
import com.onwordiesquire.android.githubsearch.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun provideSearchFragment(): SearchFragment
}