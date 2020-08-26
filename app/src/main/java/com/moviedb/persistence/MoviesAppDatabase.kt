package com.moviedb.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moviedb.persistence.converters.GenreTypeConverter

@Database(entities = [Movie::class, MovieStatus::class, Genre::class], version = 1, exportSchema = false)
@TypeConverters(GenreTypeConverter::class)
abstract class MoviesAppDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val genreDao: GenreDao
    abstract val movieStatusDao: MovieStatusDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesAppDatabase? = null

        fun getInstance(context: Context): MoviesAppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesAppDatabase::class.java,
                        "movies_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}