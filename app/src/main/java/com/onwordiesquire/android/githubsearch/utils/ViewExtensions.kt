package com.onwordiesquire.android.githubsearch.utils

import android.support.annotation.DrawableRes
import android.support.design.widget.TextInputLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

val TextInputLayout.textValue
    get() = this.editText?.text.toString()

fun ImageView.load(imageUrl: String, @DrawableRes placeholderId: Int = 0) {
    GlideApp.with(context)
            .load(imageUrl)
            .fitCenter()
            .placeholder(placeholderId)
            .into(this)
}

fun ViewGroup.inflate(layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)


fun View.disable() {
    this.isEnabled = false
}

fun View.enable() {
    this.isEnabled = true
}