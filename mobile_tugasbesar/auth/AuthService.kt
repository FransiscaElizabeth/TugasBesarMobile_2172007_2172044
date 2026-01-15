package com.example.mobile_tugasbesar.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthService {
    private val auth: FirebaseAuth = Firebase.auth

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    fun signOut() {
        auth.signOut()
    }

    /**
     * Initiates GitHub Sign-In.
     * Requires GitHub Auth enabled in Firebase Console + Client ID/Secret from GitHub.
     */
    fun signInWithGithub(activity: Activity, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val provider = OAuthProvider.newBuilder("github.com")

        // Check if there is a pending result (e.g. after rotation)
        val pendingResultTask = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError(it) }
        } else {
            auth.startActivityForSignInWithProvider(activity, provider.build())
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError(it) }
        }
    }

    /**
     * Initiates Google Sign-In via Firebase Provider (simplified).
     * For production apps, use GoogleSignInOptions/CredentialManager for better UX.
     */
    fun signInWithGoogle(activity: Activity, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val provider = OAuthProvider.newBuilder("google.com")

        auth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }
}