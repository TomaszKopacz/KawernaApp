package com.tomaszkopacz.kawernaapp.data.source

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.Score
import java.lang.Exception

interface ScoresSource {

    suspend fun addScore(score: Score): Result<Score>
    suspend fun getScoresByPlayer(player: Player): Result<List<Score>>
}