package com.example.mobile_tugasbesar.data

import com.example.mobile_tugasbesar.data.local.GenreDao
import com.example.mobile_tugasbesar.data.network.BookApi
import kotlinx.coroutines.flow.Flow

class GenreRepository(
    private val api: BookApi,
    private val dao: GenreDao
) {
    // Single Source of Truth: UI observes Database
    val genres: Flow<List<BookGenre>> = dao.getAllGenres()

    suspend fun refreshGenres() {
        try {
            val remoteGenres = api.getGenres()
            dao.insertAll(remoteGenres)
        } catch (e: Exception) {
            // In a real app, emit this error to a StateFlow for the UI to show a Snack bar
            e.printStackTrace()
        }
    }
}