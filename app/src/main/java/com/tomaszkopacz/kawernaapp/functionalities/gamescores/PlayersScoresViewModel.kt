package com.tomaszkopacz.kawernaapp.functionalities.gamescores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlayersScoresViewModel(
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private var _scores: ArrayList<Score> = ArrayList()

    var currentCategory: MutableLiveData<ScoreCategory> = MutableLiveData()
    var scores: MutableLiveData<ArrayList<Score>> = MutableLiveData()
    var state: MutableLiveData<String> = MutableLiveData()

    init {
        currentCategory.value = ScoreCategory.LIVESTOCK
        state.value = NONE
    }

    fun initGame(gameId: String, players: ArrayList<Player>) {
        val currentDate = getCurrentDateString()
        val playersCount = players.size

        for (player in players)
            this._scores.add(Score(player.email, gameId, currentDate, playersCount))

        this.scores.value = _scores
    }

    private fun getCurrentDateString(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun updateCurrentScore(position: Int, score: Int) {
        when (currentCategory.value) {
            ScoreCategory.LIVESTOCK -> _scores[position].livestock = score
            ScoreCategory.LIVESTOCK_LACK -> _scores[position].livestockLack = (-2) * score
            ScoreCategory.CEREAL -> _scores[position].cereal = score / 2 + score % 2
            ScoreCategory.VEGETABLES -> _scores[position].vegetables = score
            ScoreCategory.RUBIES -> _scores[position].rubies = score
            ScoreCategory.DWARFS -> _scores[position].dwarfs = score
            ScoreCategory.AREAS -> _scores[position].areas = score
            ScoreCategory.UNUSED_AREAS -> _scores[position].unusedAreas = (-1) * score
            ScoreCategory.PREMIUM_AREAS -> _scores[position].premiumAreas = score
            ScoreCategory.GOLD -> _scores[position].gold = score
            ScoreCategory.BEG -> _scores[position].begs = (-3) * score
        }

        this.scores.value = _scores
    }

    fun previousCategory() {
        val currentCategoryIndex = currentCategory.value!!.ordinal
        currentCategory.postValue(ScoreCategory.values()[currentCategoryIndex.dec()])
        sortScores()
    }

    fun nextCategory() {
        val currentCategoryIndex = currentCategory.value!!.ordinal
        currentCategory.postValue(ScoreCategory.values()[currentCategoryIndex.inc()])
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
        saveScoresToFireStore()
    }

    private fun saveScoresToFireStore() {

        for (score in _scores)
            fireStoreRepository.addScore(score, object : FireStoreRepository.UploadScoreListener {
                override fun onSuccess(score: Score) {
                    state.value =
                        SCORE_UPLOADED
                }

                override fun onFailure(exception: Exception) {
                    state.value =
                        FAILED_TO_UPLOAD_SCORE
                }
            })
    }

    companion object {
        const val NONE = ""
        const val SCORE_UPLOADED = "Scores submitted"
        const val FAILED_TO_UPLOAD_SCORE = "Failed to submit scores"
    }
}