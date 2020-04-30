package com.moviedb.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDao {
    @Insert
    fun insert(movie: Movie)

    @Insert
    fun insertAll(movies: List<Movie>)

    @Update
    fun update(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun clear()

    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): LiveData<List<Movie>>
}