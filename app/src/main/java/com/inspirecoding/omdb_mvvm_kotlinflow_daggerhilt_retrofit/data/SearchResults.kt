package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model

data class SearchResults(
    val Response: String? = "",
    val Search: List<Search>? = listOf(),
    val totalResults: String? = ""
)