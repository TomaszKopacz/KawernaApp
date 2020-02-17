package com.tomaszkopacz.kawernaapp.managers

import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.repository.ScoresRepository
import com.tomaszkopacz.kawernaapp.di.ActivityScope
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ActivityScope
class AccountManager @Inject constructor(
    private val repository: ScoresRepository,
    private val networkManager: NetworkManager
) {

    private var scoreChosen: Score? = null

    suspend fun getUsersScores(player: Player): Result<List<Score>> {
        return if (networkManager.isNetworkConnected()) {
            downloadScores(player)

        } else {
            Result.Failure(Message(Message.NO_INTERNET_CONNECTION))
        }
    }

    private suspend fun downloadScores(player: Player): Result<List<Score>> {
        return when (val result = repository.getScores(player)) {
            is Result.Success -> {
                Result.Success(sortScores(result.data as ArrayList))
            }

            is Result.Failure -> {
                result
            }
        }
    }

    private fun sortScores(scores: ArrayList<Score>): List<Score> {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        scores.sortByDescending { score ->
            sdf.parse(score.date)
        }

        return scores
    }

    fun setScoreChosen(score: Score) {
        this.scoreChosen = score
    }

    fun getScoreChosen(): Score? {
        return scoreChosen
    }
}