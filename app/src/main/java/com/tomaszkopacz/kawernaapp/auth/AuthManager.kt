package com.tomaszkopacz.kawernaapp.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthManager {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String, listener: AuthListener?) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) listener?.onSuccess()
                if (task.isCanceled) listener?.onFailure()
                if (task.isComplete) listener?.onSuccess()
            }
            .addOnCanceledListener { listener?.onFailure() }
    }

    fun getLoggedUser(): FirebaseUser? {
        return auth.currentUser
    }

    interface AuthListener {
        fun onSuccess()
        fun onFailure()
    }

}