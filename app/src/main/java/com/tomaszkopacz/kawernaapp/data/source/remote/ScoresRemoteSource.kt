package com.tomaszkopacz.kawernaapp.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.source.ScoresSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoresRemoteSource @Inject constructor() :
    ScoresSource {

    private val database = FirebaseFirestore.getInstance()

    override fun addScore(score: Score, listener: ScoresSource.ScoresListener?) {
        database.collection(SCORES_COLLECTION).document()
            .set(score)
            .addOnSuccessListener { listener?.onSuccess(arrayListOf(score)) }
            .addOnFailureListener { exception -> listener?.onFailure(exception) }
    }

    override fun getScoresByPlayer(player: Player, listener: ScoresSource.ScoresListener?) {
        val scores = ArrayList<Score>()

        database.collection(SCORES_COLLECTION)
            .whereEqualTo("player", player.email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    scores.add(document.toObject(Score::class.java))
                }
                listener?.onSuccess(scores)
            }
            .addOnFailureListener { exception ->
                listener?.onFailure(exception)
            }
    }

    companion object {
        const val SCORES_COLLECTION = "Scores"
    }
}