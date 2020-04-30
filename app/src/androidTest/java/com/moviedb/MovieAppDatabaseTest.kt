package com.moviedb

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.moviedb.persistence.Movie
import com.moviedb.persistence.MovieDao
import com.moviedb.persistence.MoviesAppDatabase

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieAppDatabaseTest {
    private lateinit var db: MoviesAppDatabase
    private lateinit var dao: MovieDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, MoviesAppDatabase::class.java)
            .allowMainThreadQueries().build()
        dao = db.movieDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //Tests use getAllMovies return type = List<Movie> instead of Livedata<List<Movie>>
    @Test
    @Throws(Exception::class)
    fun insertAndGetMovie() {
        val movie = Movie(null, null, null, null, null, 1, null, null, null, null, null, null, null)
        dao.insert(movie)
//        assertEquals("Test 1: Add first movie, size: 1", 1, dao.getAllMovies().size)
        val movie2 =
            Movie(null, null, null, null, null, 2, null, null, null, null, null, null, null)
        dao.insert(movie2)
//        assertEquals("Test 2: Add second movie, size: 2", 2, dao.getAllMovies().size)
        dao.clear()
//        assertEquals("Test 4: Delete all, size: 0", 0, dao.getAllMovies().size)
        dao.insertAll(listOf(movie, movie2))
//        assertEquals("Test 5: Insert all, size: 2", 2, dao.getAllMovies().size)
    }
}
