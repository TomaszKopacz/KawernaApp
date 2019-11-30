package com.tomaszkopacz.kawernaapp.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class FireStoreRepository {

    companion object {
        const val SCORES_COLLECTION = "Scores"
        const val PLAYERS_COLLECTION = "Players"
    }

    private val database = FirebaseFirestore.getInstance()

    fun addScore(score: Score, listener: UploadScoreListener?) {
        database.collection(SCORES_COLLECTION).document()
            .set(score)
            .addOnSuccessListener { listener?.onSuccess(score) }
            .addOnFailureListener { exception ->  listener?.onFailure(exception) }
    }

    fun getScores(player: String, listener: DownloadScoresListener?) {
        val scores = ArrayList<Score>()

        database.collection(SCORES_COLLECTION)
            .whereEqualTo("player", player)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) scores.add(document.toObject(Score::class.java))
                listener?.onSuccess(scores)
            }
            .addOnFailureListener { exception -> listener?.onFailure(exception) }

    }

    interface UploadScoreListener {
        fun onSuccess(score: Score)
        fun onFailure(exception: Exception)
    }

    interface DownloadScoresListener {
        fun onSuccess(scores: ArrayList<Score>)
        fun onFailure(exception: Exception)
    }
}