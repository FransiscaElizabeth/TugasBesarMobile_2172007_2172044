package com.example.mobile_tugasbesar

import com.example.mobile_tugasbesar.data.BookGenre
import com.example.mobile_tugasbesar.data.GenreRepository
import com.example.mobile_tugasbesar.frontend.GenreViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GenreViewModelTest {

    // Test dispatcher to control coroutine execution timing
    private val testDispatcher = StandardTestDispatcher()

    // Mock the Repository (dependencies)
    private val repository = mockk<GenreRepository>(relaxed = true)

    private lateinit var viewModel: GenreViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState updates when repository emits data`() = runTest {
        // Arrange: Define what the mock repo returns
        val fakeGenres = listOf(
            BookGenre("1", "Horror", "Scary books"),
            BookGenre("2", "Comedy", "Funny books")
        )
        every { repository.genres } returns flowOf(fakeGenres)

        // Act: Initialize ViewModel
        viewModel = GenreViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle() // Wait for coroutines

        // Assert: Check if ViewModel state matches mock data
        assertEquals(fakeGenres, viewModel.uiState.value)
    }

    @Test
    fun `refreshData calls repository refreshGenres`() = runTest {
        // Arrange
        every { repository.genres } returns flowOf(emptyList())
        viewModel = GenreViewModel(repository)

        // Act
        viewModel.refreshData()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Verify the function was called exactly once
        coVerify(exactly = 1) { repository.refreshGenres() }
    }
}