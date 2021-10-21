package com.jodel.jodelchallenge

import androidx.multidex.MultiDexApplication
import com.jodel.jodelchallenge.di.*
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JodelApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin {
            androidContext(this@JodelApplication)
            androidLogger()
            modules(networkModules, repositoryModules, viewModelModules, coroutineModules, dbModules)
        }
    }
}