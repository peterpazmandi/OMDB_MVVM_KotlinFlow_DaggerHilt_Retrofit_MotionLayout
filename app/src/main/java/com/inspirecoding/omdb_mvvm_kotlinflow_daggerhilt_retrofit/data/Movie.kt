package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model

data class Movie(
    val Actors: String = "",
    val Awards: String = "",
    val BoxOffice: String? = "",
    val Country: String? = "",
    val DVD: String? = "",
    val Director: String = "",
    val Genre: String? = "",
    val Language: String? = "",
    val Metascore: String? = "",
    val Plot: String? = "",
    val Poster: String? = "",
    val Production: String? = "",
    val Rated: String? = "",
    val Ratings: List<Rating>? = listOf(),
    val Released: String? = "",
    val Response: String? = "",
    val Runtime: String? = "",
    val Title: String? = "",
    val Type: String? = "",
    val Website: String? = "",
    val Writer: String? = "",
    val Year: String? = "",
    val imdbID: String? = "",
    val imdbRating: String? = "",
    val imdbVotes: String? = ""
)