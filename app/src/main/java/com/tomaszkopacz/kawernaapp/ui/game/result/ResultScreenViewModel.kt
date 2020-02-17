package com.tomaszkopacz.kawernaapp.ui.game.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.managers.GameManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResultScreenViewModel @Inject constructor(
    private val gameManager: GameManager
) : ViewModel() {

    var resultScores = MutableLiveData<ArrayList<PlayerScore>>()

    var state = MutableLiveData<String>()

    init {
        exposeResultScores(gameManager.getPlayersScores())
    }

    private fun exposeResultScores(playersScores: ArrayList<PlayerScore>) {
        resultScores.postValue(playersScores)
    }

    fun submit() {
        GlobalScope.launch {
            when (gameManager.submitResult()) {
                is Result.Success -> {
                    state.postValue(Message.GAME_SUBMITTED)
                }

                is Result.Failure -> {
                    state.postValue(Message.GAME_SUBMITION_FAILED)
                }
            }
        }
    }
}