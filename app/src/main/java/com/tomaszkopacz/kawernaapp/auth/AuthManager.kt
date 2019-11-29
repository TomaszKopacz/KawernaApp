package com.tomaszkopacz.kawernaapp.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthManager {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String, listener: AuthListener?) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) listener?.onSuccess()
                if (task.isCanceled) listener?.onFailure()
            }
            .addOnCanceledListener { listener?.onFailure() }
    }

    fun loginUser(email: String, password: String, listener: AuthListener?) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) listener?.onSuccess()
                else listener?.onFailure()
            }
    }

    fun getLoggedUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logoutUser() {
        auth.signOut()
    }

    interface AuthListener {
        fun onSuccess()
        fun onFailure()
    }

}