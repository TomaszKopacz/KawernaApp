package com.tomaszkopacz.kawernaapp.data.repository

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.source.ScoresSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoresRepository @Inject constructor(
    private val scoresSource: ScoresSource
) {
    fun addScores(scores: List<Score>, listener: ScoresSource.ScoresListener?) {
        for (score in scores)
            scoresSource.addScore(score, listener)
    }

    fun addPlayersScores(playersScores: List<PlayerScore>, listener: ScoresSource.ScoresListener?) {
        for (playerScore in playersScores)
            scoresSource.addScore(playerScore.score, listener)
    }

    fun getScores(player: Player, listener: ScoresSource.ScoresListener) {
        scoresSource.getScoresByPlayer(player, listener)
    }
}