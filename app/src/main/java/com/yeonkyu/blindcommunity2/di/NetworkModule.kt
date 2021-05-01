package com.yeonkyu.blindcommunity2.di

import com.yeonkyu.blindcommunity2.data.api.AuthService
import com.yeonkyu.blindcommunity2.data.api.BoardService
import com.yeonkyu.blindcommunity2.data.api.PostService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://13.125.232.199:3000/"

val networkModule: Module = module {

    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply { HttpLoggingInterceptor.Level.BASIC }

    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
//            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30,TimeUnit.SECONDS)
            .build()

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    fun provideSplashService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    fun provideBoardService(retrofit: Retrofit): BoardService =
        retrofit.create(BoardService::class.java)

    fun providePostService(retrofit: Retrofit): PostService =
        retrofit.create(PostService::class.java)

    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideSplashService(get()) }
    single { provideBoardService(get()) }
    single { providePostService(get()) }
}