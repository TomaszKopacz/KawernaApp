package com.tomaszkopacz.kawernaapp.data.repository

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.source.ScoresSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoresRepository @Inject constructor(
    private val scoresSource: ScoresSource
) {

    suspend fun addPlayersScores(playersScores: List<PlayerScore>): Result<List<PlayerScore>> {
        for (playerScore in playersScores)
            scoresSource.addScore(playerScore.score)

        return Result.Success(playersScores)
    }

    suspend fun getScores(player: Player): Result<List<Score>> {
        return scoresSource.getScoresByPlayer(player)
    }
}