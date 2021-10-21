package com.jodel.jodelchallenge.network.helper

import android.content.Context
import com.jodel.jodelchallenge.util.Constant.CONNECTION_TIMEOUT_SEC
import com.jodel.jodelchallenge.util.Constant.GIT_HUB_PERSONAL_TOKEN
import com.jodel.jodelchallenge.util.Constant.READ_TIMEOUT_SEC
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseNetworkHelper<S>(
    val context: Context,
    val serviceClass: Class<S>
) {

    abstract fun createHelperService(): S

    protected fun createHelper(baseUrl: String): S {
        val client = createOkHttpClient()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        return retrofit.create(serviceClass)
    }

    private fun createOkHttpClient(): OkHttpClient.Builder {

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
        httpClient.readTimeout(READ_TIMEOUT_SEC, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", GIT_HUB_PERSONAL_TOKEN)
                .build()
            chain.proceed(request)
        }

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        return httpClient
    }
}