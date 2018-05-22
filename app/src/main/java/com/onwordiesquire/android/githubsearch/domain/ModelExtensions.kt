package com.onwordiesquire.android.githubsearch.domain

fun Boolean?.orDefault(default: Boolean = false) = this ?: default
fun Int?.orDefault(default: Int = -1) = this ?: default
fun Long?.orDefault(default: Long = -1) = this ?: default
fun Double?.orDefault(default: Double = 0.0) = this ?: default
fun String?.orDefault(default: String = "") = this ?: default
fun <T> List<T>?.orDefault(default: List<T> = listOf()) = this ?: default