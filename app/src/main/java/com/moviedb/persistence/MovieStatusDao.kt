package com.moviedb.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MovieStatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieStatus: List<MovieStatus>)
}