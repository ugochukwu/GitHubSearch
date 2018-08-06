package com.onwordiesquire.android.githubsearch.utils

import kotlinx.coroutines.DefaultDispatcher
import kotlinx.coroutines.withContext


suspend fun <T> executeAsync(suspendingFunction: suspend () -> T): T =
        withContext(DefaultDispatcher) {
            suspendingFunction()
        }
