package com.onwordiesquire.android.githubsearch.utils

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragment(fragment: Fragment,
                                      @IdRes containerId: Int,
                                      tag: String = fragment.javaClass.simpleName) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.apply {
        replace(containerId, fragment, tag)
        commit()
    }
}
