package com.onwordiesquire.android.githubsearch.di.modules

import android.arch.lifecycle.ViewModel
import com.onwordiesquire.android.githubsearch.ui.base.ViewModelKey
import com.onwordiesquire.android.githubsearch.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun provideSearchViewModel(searchViewModel: SearchViewModel): ViewModel
}