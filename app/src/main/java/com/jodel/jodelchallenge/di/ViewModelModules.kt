package com.jodel.jodelchallenge.di

import com.jodel.jodelchallenge.ui.vm.UserDetailViewModel
import com.jodel.jodelchallenge.ui.vm.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { UserListViewModel( get(), get(), get()) }
    viewModel { UserDetailViewModel(get(), get()) }
}
