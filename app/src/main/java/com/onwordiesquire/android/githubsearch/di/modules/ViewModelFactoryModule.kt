package com.onwordiesquire.android.githubsearch.di.modules

import android.arch.lifecycle.ViewModelProvider
import com.onwordiesquire.android.githubsearch.ui.base.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}