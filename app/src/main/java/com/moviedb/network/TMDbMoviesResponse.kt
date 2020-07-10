package com.moviedb.network

data class TMDbMoviesResponse(
    val page: Int,
    val results: List<TMDbMovie>,
    val total_results: Int,
    val total_pages: Int
)