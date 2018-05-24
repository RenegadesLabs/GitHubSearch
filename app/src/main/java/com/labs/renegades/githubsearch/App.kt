package com.labs.renegades.githubsearch

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.GsonBuilder
import com.labs.renegades.githubsearch.datasource.local.room.AppDatabase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "repositories").build()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthHeaderRequestInterceptor())
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private inner class AuthHeaderRequestInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            builder.addHeader("Authorization", "Bearer 21b61db73577983a095652793126dc3d99f0dbec")
            builder.addHeader("Content-Type", "application/json")
            val request = builder.build()
            return chain.proceed(request)
        }
    }

    companion object {
        var retrofit: Retrofit? = null
        lateinit var database: AppDatabase
    }
}