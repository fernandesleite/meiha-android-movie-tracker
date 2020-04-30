package com.moviedb.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_response_table")
data class MovieResponse(
    @PrimaryKey val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
)