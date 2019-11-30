package com.tomaszkopacz.kawernaapp.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

object AuthManager {

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

    fun getLoggedUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logoutUser() {
        auth.signOut()
    }

    interface AuthListener {
        fun onSuccess(user: FirebaseUser)
        fun onFailure(exception: Exception)
    }
}