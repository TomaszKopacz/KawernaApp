package com.tomaszkopacz.kawernaapp.functionalities.newgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Categories
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score
import java.lang.Exception

class PlayersScoresViewModel : ViewModel() {

    private var _scores: ArrayList<Score> = ArrayList()

    var currentCategory: MutableLiveData<Int> = MutableLiveData()
    var scores: MutableLiveData<ArrayList<Score>> = MutableLiveData()
    var state: MutableLiveData<String> = MutableLiveData()

    init {
        currentCategory.value = Categories.LIVESTOCK
        state.value = NONE
    }

    fun initGame(players: ArrayList<Player>) {
        val gameId = generateUniqueGameId()
        val playersCount = players.size

        for (player in players)
            this._scores.add(Score(player.email, gameId, playersCount))

        this.scores.value = _scores
    }

    private fun generateUniqueGameId(): String {
        val millis = System.currentTimeMillis().toString()
        val random = (0..1000000).random().toString()
        return millis + random
    }

    fun updateCurrentScore(position: Int, score: Int) {
        when (currentCategory.value) {
            Categories.LIVESTOCK -> _scores[position].livestock = score
            Categories.LIVESTOCK_LACK -> _scores[position].livestockLack = (-2) * score
            Categories.CEREAL -> _scores[position].cereal = score / 2 + score % 2
            Categories.VEGETABLES -> _scores[position].vegetables = score
            Categories.RUBIES -> _scores[position].rubies = score
            Categories.DWARFS -> _scores[position].dwarfs = score
            Categories.AREAS -> _scores[position].areas = score
            Categories.UNUSED_AREAS -> _scores[position].unusedAreas = (-1) * score
            Categories.PREMIUM_AREAS -> _scores[position].premiumAreas = score
            Categories.GOLD -> _scores[position].gold = score
            Categories.BEGS -> _scores[position].begs = (-3) * score
        }

        this.scores.value = _scores
    }

    fun previousCategory() {
        currentCategory.value = currentCategory.value!!.dec()
        sortScores()
    }

    fun nextCategory() {
        currentCategory.value = currentCategory.value!!.inc()
        updateUsersPlaces()
        sortScores()
    }

    private fun updateUsersPlaces() {
        val sortedScores = _scores.sortedByDescending { it.total() }
        sortedScores.forEachIndexed { index, score ->
            score.place = index + 1
        }
    }

    private fun sortScores() {
        _scores.sortBy { it.place }
        scores.value = _scores
    }

    fun submitScores() {
        saveToFireStore()
    }

    private fun saveToFireStore() {

        for (score in _scores)
            FireStoreRepository().addScore(score, object : FireStoreRepository.UploadScoreListener {
                override fun onSuccess(score: Score) {
                    state.value = SCORE_UPLOADED
                }

                override fun onFailure(exception: Exception) {
                    state.value = FAILED_TO_UPLOAD_SCORE
                }
            })
    }

    companion object {
        const val NONE = ""
        const val SCORE_UPLOADED = "Scores submitted"
        const val FAILED_TO_UPLOAD_SCORE = "Failed to submit scores"
    }
}