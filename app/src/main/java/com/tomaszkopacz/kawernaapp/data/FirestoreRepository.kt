package com.tomaszkopacz.kawernaapp.data

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    companion object {
        const val SCORES_COLLECTION = "Scores"
        const val PLAYERS_COLLECTION = "Players"
    }

    private val firestore = FirebaseFirestore.getInstance()

    fun addScore(score: Score) {
        firestore.collection(SCORES_COLLECTION).add(score)
    }
}