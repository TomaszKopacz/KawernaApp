package com.tomaszkopacz.kawernaapp.functionalities.resultscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.data.Score
import java.lang.Exception

class ResultScreenViewModel : ViewModel() {

    private var mPlayers = ArrayList<Player>()
    private var mScores = ArrayList<Score>()
    private var mResultScores = ArrayList<PlayerScore>()

    var resultScores = MutableLiveData<ArrayList<PlayerScore>>()

    fun showGameResults(gameId: String, players: ArrayList<Player>) {
        mPlayers = players
        FireStoreRepository().getGameScores(gameId, listener)
    }

    private val listener = object : FireStoreRepository.DownloadScoresListener {
        override fun onSuccess(scores: ArrayList<Score>) {
            scores.sortBy { score -> score.place }
            mScores = scores

            for (score in mScores) {
                for (player in mPlayers)
                    if (player.email == score.player) mResultScores.add(PlayerScore(player, score))
            }

            resultScores.postValue(mResultScores)
        }

        override fun onFailure(exception: Exception) {

        }

    }
}