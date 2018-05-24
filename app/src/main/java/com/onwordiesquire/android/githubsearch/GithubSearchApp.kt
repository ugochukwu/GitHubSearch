package com.onwordiesquire.android.githubsearch

import android.app.Activity
import android.app.Application
import com.onwordiesquire.android.githubsearch.di.component.DaggerMainComponent
import com.onwordiesquire.android.githubsearch.utils.MyLogger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class GithubSearchApp : Application(), MyLogger, HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

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