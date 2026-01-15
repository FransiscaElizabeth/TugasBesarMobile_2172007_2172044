package com.example.mobile_tugasbesar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobile_tugasbesar.data.BookGenre

@Database(entities = [BookGenre::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
}