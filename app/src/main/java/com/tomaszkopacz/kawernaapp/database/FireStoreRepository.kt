package com.tomaszkopacz.kawernaapp.database

import com.google.firebase.firestore.FirebaseFirestore
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score

class FireStoreRepository : DataBaseRepository{

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

    override fun getUsersScores(player: Player, listener: DataBaseRepository.ScoresListener?) {
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
            .addOnFailureListener {
                    exception -> listener?.onFailure(exception)
            }
    }

    fun getGameScores(gameId: String, listener: DownloadScoresListener?) {
        val scores = ArrayList<Score>()

        database.collection(SCORES_COLLECTION)
            .whereEqualTo("game", gameId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) scores.add(document.toObject(Score::class.java))
                listener?.onSuccess(scores)
            }
            .addOnFailureListener { exception -> listener?.onFailure(exception) }
    }

    override fun addPlayer(player: Player, listener: DataBaseRepository.PlayerListener?) {
        database.collection(PLAYERS_COLLECTION).document()
            .set(player)
            .addOnSuccessListener { listener?.onSuccess(player) }
            .addOnFailureListener { exception -> listener?.onFailure(exception) }
    }

    override fun getPlayerByEmail(email: String, listener: DataBaseRepository.PlayerListener?) {
        database.collection(PLAYERS_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    val player = documents.documents[0].toObject(Player::class.java)
                    listener?.onSuccess(player!!)
                }

            }
            .addOnFailureListener { exception ->
                listener?.onFailure(exception)
            }
    }

    interface UploadScoreListener {
        fun onSuccess(score: Score)
        fun onFailure(exception: Exception)
    }

    interface DownloadScoresListener {
        fun onSuccess(scores: ArrayList<Score>)
        fun onFailure(exception: Exception)
    }

    interface UploadPlayerListener {
        fun onSuccess(player: Player)
        fun onFailure(exception: Exception)
    }

    interface DownloadPlayerListener {
        fun onSuccess(player: Player?)
        fun onFailure(exception: Exception)
    }
}