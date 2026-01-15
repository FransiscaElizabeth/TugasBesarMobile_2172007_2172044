package com.example.mobile_tugasbesar.frontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobile_tugasbesar.data.BookGenre
import com.example.mobile_tugasbesar.data.GenreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GenreViewModel(private val repository: GenreRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<List<BookGenre>>(emptyList())
    val uiState: StateFlow<List<BookGenre>> = _uiState

    init {
        // Collect data from Room
        viewModelScope.launch {
            repository.genres.collect { genres ->
                _uiState.value = genres
            }
        }
        // Trigger API fetch
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            repository.refreshGenres()
        }
    }
}

class GenreViewModelFactory(private val repository: GenreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GenreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GenreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}