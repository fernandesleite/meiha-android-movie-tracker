package com.moviedb.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_status")
data class MovieStatus (
    @PrimaryKey
    val id: Int,
    val status: String
){
    companion object {
        fun populateData() : List<MovieStatus>{
            return mutableListOf<MovieStatus>(
                MovieStatus(0, "No Category"),
                MovieStatus(1, "Watched"),
                MovieStatus(2, "To Watch")
            )
        }
    }
}