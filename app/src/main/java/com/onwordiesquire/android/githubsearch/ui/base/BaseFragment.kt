package com.onwordiesquire.android.githubsearch.ui.base

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection


abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    abstract fun injectViewModel(): ViewModel
}