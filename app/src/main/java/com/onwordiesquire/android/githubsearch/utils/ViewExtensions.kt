package com.onwordiesquire.android.githubsearch.utils

import android.support.design.widget.TextInputLayout
import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

val TextInputLayout.textValue
    get() = this.editText?.text.toString()