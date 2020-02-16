package com.tomaszkopacz.kawernaapp.data.source

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score
import java.lang.Exception

interface ScoresSource {

    fun addScore(score: Score, listener: ScoresListener?)
    fun getScoresByPlayer(player: Player, listener: ScoresListener?)

    interface ScoresListener {
        fun onSuccess(scores: ArrayList<Score>)
        fun onFailure(exception: Exception)
    }
}