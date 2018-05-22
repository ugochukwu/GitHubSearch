package com.onwordiesquire.android.githubsearch

import android.app.Application
import com.onwordiesquire.android.githubsearch.di.DaggerMainComponent
import com.onwordiesquire.android.githubsearch.utils.MyLogger

class GithubSearchApp : Application(), MyLogger {

    private val mainComponent by lazy {
        DaggerMainComponent
                .builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        mainComponent.inject(this)
    }
}