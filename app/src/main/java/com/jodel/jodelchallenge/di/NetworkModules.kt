package com.jodel.jodelchallenge.di


import com.jodel.jodelchallenge.network.helper.JodelNetworkHelper
import org.koin.dsl.module

val networkModules = module {
    single { JodelNetworkHelper(get()).createHelperService() }
}