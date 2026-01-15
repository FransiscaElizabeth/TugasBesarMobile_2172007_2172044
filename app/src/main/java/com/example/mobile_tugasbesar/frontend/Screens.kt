package com.example.mobile_tugasbesar.frontend

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobile_tugasbesar.auth.AuthService
import com.example.mobile_tugasbesar.data.BookGenre

@Composable
fun AppNavigation(
    authService: AuthService,
    viewModel: GenreViewModel
) {
    var isLoggedIn by remember { mutableStateOf(authService.isUserLoggedIn()) }

    if (isLoggedIn) {
        HomeScreen(
            viewModel = viewModel,
            onLogout = {
                authService.signOut()
                isLoggedIn = false
            }
        )
    } else {
        LoginScreen(
            authService = authService,
            onLoginSuccess = { isLoggedIn = true }
        )
    }
}

@Composable
fun LoginScreen(authService: AuthService, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Book Genre Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                activity?.let {
                    authService.signInWithGithub(it, onLoginSuccess) { e ->
                        Toast.makeText(context, "GitHub Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        ) {
            Text("Sign in with GitHub")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                activity?.let {
                    authService.signInWithGoogle(it, onLoginSuccess) { e ->
                        Toast.makeText(context, "Google Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        ) {
            Text("Sign in with Google")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: GenreViewModel, onLogout: () -> Unit) {
    val genres by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Genres") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Text("Log Out")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                Button(
                    onClick = { viewModel.refreshData() },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text("Refresh Data")
                }
            }
            items(genres) { genre ->
                GenreItem(genre)
            }
        }
    }
}

@Composable
fun GenreItem(genre: BookGenre) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = genre.name, style = MaterialTheme.typography.titleLarge)
            if (!genre.description.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = genre.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}