package com.jodel.jodelchallenge.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

abstract  class BaseFragment<T: ViewModel, TBinding: ViewDataBinding>: Fragment() {

    protected lateinit var binding: TBinding
    protected lateinit var viewModel: T

    protected abstract val layoutId: Int

    abstract val viewModelClass: KClass<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel()
        bindViewModelData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(savedInstanceState)
    }

    protected open fun bindViewModelData() {}

    abstract fun viewModel(): T

    abstract fun initViews(savedInstanceState: Bundle?)
}