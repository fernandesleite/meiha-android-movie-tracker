package com.moviedb.network

data class TMDbMovieDetails(
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Long,
    val genres: List<TMDbGenre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompanies>,
    val spoken_languages: List<SpokenLanguages>,
    val release_date: String,
    val revenue: Long,
    val runtime: Int?,
    val status: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val recommendations: TMDbMovieRecommendations,
    val credits: TMDbMovieCredits
) {
    data class ProductionCompanies(
        val name: String,
        val id: Int,
        val logo_path: String?,
        val origin_country: String
    )

    data class SpokenLanguages(
        val iso_639_1: String,
        val name: String
    )
}