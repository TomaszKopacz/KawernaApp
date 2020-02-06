package com.tomaszkopacz.kawernaapp.functionalities.game.scores

import androidx.lifecycle.MutableLiveData
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.game.GameManager

class PlayersScoresViewModel(
    private val gameManager: GameManager
) {

    var state: MutableLiveData<String> = MutableLiveData()

    var playersScores: MutableLiveData<ArrayList<PlayerScore>> = MutableLiveData()

    var currentCategory: MutableLiveData<ScoreCategory> = MutableLiveData()

    init {
        exposeCategory(ScoreCategory.ANIMALS)
        exposeScores(gameManager.getPlayersScores())
    }

    fun updateScore(position: Int, score: Int) {
        gameManager.updateScoreForCategory(position, score, currentCategory.value!!)
        exposeScores(gameManager.getPlayersScores())
    }

    fun previousCategory() {
        val currentCategoryIndex = currentCategory.value!!.ordinal
        exposeCategory(ScoreCategory.values()[currentCategoryIndex.dec()])

        gameManager.sortScores()
        exposeScores(gameManager.getPlayersScores())
    }

    fun nextCategory() {
        val currentCategoryIndex = currentCategory.value!!.ordinal
        exposeCategory(ScoreCategory.values()[currentCategoryIndex.inc()])

        gameManager.sortScores()
        exposeScores(gameManager.getPlayersScores())
    }

    fun submitScores() {
        gameManager.submitGame()
    }

    private fun exposeScores(playersScores: ArrayList<PlayerScore>) {
        this.playersScores.postValue(playersScores)
    }

    private fun exposeCategory(category: ScoreCategory) {
        currentCategory.postValue(category)
    }

    private fun scoresUploadSucceed() {
        state.postValue(STATE_SCORE_UPLOAD_SUCCEED)
    }

    private fun scoresUploadFailed() {
        state.postValue(STATE_SCORES_UPLOAD_FAILED)
    }

    companion object {
        const val STATE_NONE = ""
        const val STATE_SCORE_UPLOAD_SUCCEED = "Scores submitted"
        const val STATE_SCORES_UPLOAD_FAILED = "Failed to submit playersScores"
    }
}