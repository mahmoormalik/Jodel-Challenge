package com.jodel.jodelchallenge.di
import com.jodel.jodelchallenge.coroutine.CoroutinesContext
import org.koin.dsl.module

val coroutineModules = module {
    factory { CoroutinesContext() }
}