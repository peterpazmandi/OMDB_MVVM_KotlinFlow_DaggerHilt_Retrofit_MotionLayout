package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.di

import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository.MovieRepository
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ) : MovieRepository

}