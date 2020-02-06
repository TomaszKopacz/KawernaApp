package com.tomaszkopacz.kawernaapp.scores

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.database.DataBaseRepository
import java.lang.Exception

class ScoreManager(
    private val repository: DataBaseRepository) {

    private var scoresListener: ScoresListener? = null

    fun getUsersScores(player: Player, listener: ScoresListener) {
        this.scoresListener = listener
        repository.getUsersScores(player, dbScoresListener)
    }

    private val dbScoresListener = object : DataBaseRepository.ScoresListener {
        override fun onSuccess(scores: ArrayList<Score>) {
            scoresListener?.onSuccess(scores)
        }

        override fun onFailure(exception: Exception) {
            scoresListener?.onFailure(exception)
        }
    }

    interface ScoresListener {
        fun onSuccess(scores: ArrayList<Score>)
        fun onFailure(exception: Exception)
    }
}