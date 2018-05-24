package utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

class TestLiveDataWrapper<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}

fun <T> LiveData<T>.testLiveDataWrapper() = TestLiveDataWrapper<T>().also {
    observeForever(it)
}
