package com.jodel.jodelchallenge.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.bindTo(owner: LifecycleOwner, crossinline action: (T) -> Unit) =
    this.observe(owner, Observer { action(it) })
