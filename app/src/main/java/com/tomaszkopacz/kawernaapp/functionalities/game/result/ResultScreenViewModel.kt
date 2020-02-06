package com.tomaszkopacz.kawernaapp.functionalities.game.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import java.lang.Exception

class ResultScreenViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private var mPlayers = ArrayList<Player>()
    private var mScores = ArrayList<Score>()
    private var mResultScores = ArrayList<PlayerScore>()

    var resultScores = MutableLiveData<ArrayList<PlayerScore>>()

    init {
        getFinalScores()
    }

    private fun getFinalScores() {
        mPlayers = sharedPrefsRepository.getGamePlayers()!!
        val gameId = sharedPrefsRepository.getGameId()!!

        fireStoreRepository.getGameScores(gameId, object : FireStoreRepository.DownloadScoresListener {
            override fun onSuccess(scores: ArrayList<Score>) {
                scores.sortBy { score -> score.place }
                mScores = scores

                processPlayersScores()
                exposeResultScores()
            }

            override fun onFailure(exception: Exception) {

            }
        })
    }

    private fun processPlayersScores() {
        for (score in mScores) {
            for (player in mPlayers)
                if (player.email == score.player)
                    mResultScores.add(PlayerScore(player, score))
        }
    }

    private fun exposeResultScores() {
        resultScores.postValue(mResultScores)
    }

    fun submit() {
        clearMemory()
    }

    private fun clearMemory() {
        sharedPrefsRepository.clearGameId()
        sharedPrefsRepository.clearGamePlayers()
    }
}