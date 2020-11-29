package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.di

import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.search.adapter.SearchResultAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class SearchResultAdapterModuel {

    @Provides
    fun providesSearchResultAdapter() = SearchResultAdapter()

}