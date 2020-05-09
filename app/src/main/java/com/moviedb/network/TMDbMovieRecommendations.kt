package com.moviedb.network

data class TMDbMovieRecommendations(
    val page: Int,
    val results: List<TMDbMovie>
)