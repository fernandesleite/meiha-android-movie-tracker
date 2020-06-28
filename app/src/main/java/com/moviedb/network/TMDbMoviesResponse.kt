package com.moviedb.network

data class TMDbMoviesResponse(
    val page: Int,
    val results: List<TMDbMovie>
)