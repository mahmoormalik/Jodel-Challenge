package com.jodel.jodelchallenge.extension

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.jodel.jodelchallenge.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

inline fun <reified T : ViewModel, reified K : ViewDataBinding> BaseFragment<T, K>.injectViewModel(): T {
    return getViewModel(clazz = viewModelClass)
}