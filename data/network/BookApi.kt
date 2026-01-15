package com.example.mobile_tugasbesar.data.network

import com.example.mobile_tugasbesar.data.BookGenre
import retrofit2.http.GET

interface BookApi {
    @GET("genre_buku.php")
    suspend fun getGenres(): List<BookGenre>
}