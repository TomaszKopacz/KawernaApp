package com.tomaszkopacz.kawernaapp.functionalities.game.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.game.GameManager

class ResultScreenViewModel(
    private val gameManager: GameManager
) : ViewModel() {

    var resultScores = MutableLiveData<ArrayList<PlayerScore>>()

    init {
        exposeResultScores(gameManager.getPlayersScores())
    }

    private fun exposeResultScores(playersScores: ArrayList<PlayerScore>) {
        resultScores.postValue(playersScores)
    }

    fun submit() {
        gameManager.submitResult()
    }
}