package com.tomaszkopacz.kawernaapp.managers

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.database.DataBaseRepository
import com.tomaszkopacz.kawernaapp.data.Message
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class AccountManager @Inject constructor(
    private val repository: DataBaseRepository,
    private val networkManager: NetworkManager
) {

    private var scoresListener: ScoresListener? = null

    fun getUsersScores(player: Player, listener: ScoresListener) {
        this.scoresListener = listener

        if (networkManager.isNetworkConnected()) {
            repository.getScoresByPlayer(player, dbScoresListener)

        } else {
            scoresListener?.onFailure(
                Message(
                    Message.NO_INTERNET_CONNECTION
                )
            )
        }
    }

    private val dbScoresListener = object : DataBaseRepository.ScoresListener {
        override fun onSuccess(scores: ArrayList<Score>) {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            scores.sortByDescending { score ->
                sdf.parse(score.date)
            }

            scoresListener?.onSuccess(
                scores,
                Message(Message.SCORES_DOWNLOADED)
            )
        }

        override fun onFailure(exception: Exception) {
            scoresListener?.onFailure(
                Message(
                    Message.SCORES_DOWNLOAD_FAILED
                )
            )
        }
    }

    interface ScoresListener {
        fun onSuccess(scores: ArrayList<Score>, message: Message)
        fun onFailure(message: Message)
    }
}