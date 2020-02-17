package com.tomaszkopacz.kawernaapp.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.source.ScoresSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoresRemoteSource @Inject constructor() :
    ScoresSource {

    private val database = FirebaseFirestore.getInstance()

    override suspend fun addScore(score: Score): Result<Score> {
        database.collection(SCORES_COLLECTION).document().set(score).await()
        return Result.Success(score)
    }

    override suspend fun getScoresByPlayer(player: Player): Result<List<Score>> {
        val result = database.collection(SCORES_COLLECTION)
            .whereEqualTo("player", player.email)
            .get().await()

        return if (result.documents.isEmpty()) {
            Result.Failure(Message(Message.NO_SCORES))

        } else {
            val scores = ArrayList<Score>()
            for (document in result.documents) {
                scores.add(document.toObject(Score::class.java)!!)
            }

            Result.Success(scores)
        }
    }

    companion object {
        const val SCORES_COLLECTION = "Scores"
    }
}