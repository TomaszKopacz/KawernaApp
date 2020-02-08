package com.tomaszkopacz.kawernaapp.ui.game.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.managers.GameManager
import javax.inject.Inject

class ResultScreenViewModel @Inject constructor(
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