package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.di

import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository.remote.MovieEndpoints
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RemoteApiModule {

    @Provides
    @Singleton
    fun providesOkHttpClient() : OkHttpClient {

        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

    }

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit {

        return Retrofit.Builder()
            .client(providesOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun providesMovieEndpoint() : MovieEndpoints {

        return providesRetrofit().create(MovieEndpoints::class.java)

    }

}