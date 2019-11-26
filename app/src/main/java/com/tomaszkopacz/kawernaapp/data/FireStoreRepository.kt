package com.tomaszkopacz.kawernaapp.data

import com.google.firebase.firestore.FirebaseFirestore

class FireStoreRepository {

    companion object {
        const val SCORES_COLLECTION = "Scores"
        const val PLAYERS_COLLECTION = "Players"
    }

    private val database = FirebaseFirestore.getInstance()

    fun addScore(score: Score, listener: ResultListener?) {
        database.collection(SCORES_COLLECTION).document()
            .set(score)
            .addOnCompleteListener { listener?.onSuccess(score) }
            .addOnFailureListener { listener?.onFailure(score) }
    }

    fun addScores(scores: ArrayList<Score>, listener: ResultListener?) {
        for(score in scores) {
            addScore(score, object : ResultListener {
                override fun onSuccess(obj: Any?) {}
                override fun onFailure(obj: Any?) { listener?.onFailure(score)}
            })
        }

        listener?.onSuccess(scores)
    }

    interface ResultListener {
        fun onSuccess(obj: Any?)
        fun onFailure(obj: Any?)
    }
}