package com.jodel.jodelchallenge.di

import com.jodel.jodelchallenge.repository.UserRepository
import io.realm.Realm
import org.koin.dsl.module

val repositoryModules = module {
    factory { UserRepository(get(), get()) }
    factory { Realm.getDefaultInstance() }
}