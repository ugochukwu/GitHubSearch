package com.onwordiesquire.android.githubsearch

import android.os.Bundle
import com.onwordiesquire.android.githubsearch.ui.base.BaseActivity
import com.onwordiesquire.android.githubsearch.ui.search.SearchFragment
import com.onwordiesquire.android.githubsearch.utils.replaceFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            displaySearchFragment()
        }
    }

    override fun getLayout(): Int = R.layout.activity_main

    private fun displaySearchFragment() {
        replaceFragment(SearchFragment.newInstance(), containerId = R.id.contentContainer)

    }
}
