package com.tomaszkopacz.kawernaapp.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthManager {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String, listener: AuthListener?) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result -> listener?.onSuccess(result.user)  }
            .addOnFailureListener { exception -> listener?.onFailure(exception) }
    }

    fun loginUser(email: String, password: String, listener: AuthListener?) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result -> listener?.onSuccess(result.user) }
            .addOnFailureListener { exception ->  listener?.onFailure(exception) }

    }

    fun getLoggedUser(): String? {
        return auth.currentUser?.email
    }

    fun logoutUser() {
        auth.signOut()
    }

    interface AuthListener {
        fun onSuccess(user: FirebaseUser)
        fun onFailure(exception: Exception)
    }
}