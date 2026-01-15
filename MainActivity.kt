package com.example.mobile_tugasbesar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.mobile_tugasbesar.auth.AuthService
import com.example.mobile_tugasbesar.data.GenreRepository
import com.example.mobile_tugasbesar.data.local.AppDatabase
import com.example.mobile_tugasbesar.data.network.BookApi
import com.example.mobile_tugasbesar.frontend.AppNavigation
import com.example.mobile_tugasbesar.frontend.GenreViewModel
import com.example.mobile_tugasbesar.frontend.GenreViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Init Database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "book-genre-db"
        ).build()

        // 2. Init Network
        val retrofit = Retrofit.Builder()
            .baseUrl("https://web-apaya.infinityfreeapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(BookApi::class.java)

        // 3. Init Repository
        val repository = GenreRepository(api, db.genreDao())

        // 4. Init ViewModel
        val viewModel = GenreViewModelFactory(repository).create(GenreViewModel::class.java)

        // 5. Init Auth
        val authService = AuthService()

        setContent {
            AppNavigation(authService, viewModel)
        }
    }
}