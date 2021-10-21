package com.jodel.jodelchallenge.di

import com.jodel.jodelchallenge.db.DBManager
import org.koin.dsl.module

val dbModules = module {
    factory { DBManager(get(), get()) }
}