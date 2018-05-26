package com.onwordiesquire.android.githubsearch.utils

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

fun AppCompatActivity.replaceFragment(fragment: Fragment,
                                      @IdRes containerId: Int,
                                      tag: String = fragment.javaClass.simpleName) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.apply {
        replace(containerId, fragment, tag)
        commit()
    }
}

fun FragmentActivity?.hideSoftKeyboard() {
    this?.let {
        val inputMethodManager = it.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        it.currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}