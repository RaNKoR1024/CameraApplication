package com.rankor.cameraapplication

import android.app.Application
import com.rankor.cameraapplication.data.remote.OrionApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    lateinit var orionApi: OrionApi

    override fun onCreate() {
        super.onCreate()

        setupRetrofit()
    }

    // setup retrofit logging and api for calls
    private fun setupRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://orionnet.online/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        orionApi = retrofit.create(OrionApi::class.java)
    }
}