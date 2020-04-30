package com.moviedb.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesAppDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

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