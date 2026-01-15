package com.example.mobile_tugasbesar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobile_tugasbesar.data.BookGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    fun getAllGenres(): Flow<List<BookGenre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<BookGenre>)
}