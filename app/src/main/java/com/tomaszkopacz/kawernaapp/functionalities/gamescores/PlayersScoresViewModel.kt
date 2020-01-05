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

    var state: MutableLiveData<String> = MutableLiveData()

    private var _playersScores: ArrayList<PlayerScore> = ArrayList()
    var playersScores: MutableLiveData<ArrayList<PlayerScore>> = MutableLiveData()

    var currentCategory: MutableLiveData<ScoreCategory> = MutableLiveData()

    init {
        currentCategory.value = ScoreCategory.LIVESTOCK
        state.value = NONE
    }

    fun initGame(gameId: String, players: ArrayList<Player>) {
        val currentDate = getCurrentDateString()
        val playersCount = players.size

        for (player in players)
            this._playersScores.add(PlayerScore(player, Score(player.email, gameId, currentDate, playersCount)))

        this.playersScores.value = _playersScores
    }

    private fun getCurrentDateString(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun updateCurrentScore(position: Int, score: Int) {
        when (currentCategory.value) {
            ScoreCategory.LIVESTOCK -> _playersScores[position].score.livestock = score
            ScoreCategory.LIVESTOCK_LACK -> _playersScores[position].score.livestockLack = (-2) * score
            ScoreCategory.CEREAL -> _playersScores[position].score.cereal = score / 2 + score % 2
            ScoreCategory.VEGETABLES -> _playersScores[position].score.vegetables = score
            ScoreCategory.RUBIES -> _playersScores[position].score.rubies = score
            ScoreCategory.DWARFS -> _playersScores[position].score.dwarfs = score
            ScoreCategory.AREAS -> _playersScores[position].score.areas = score
            ScoreCategory.UNUSED_AREAS -> _playersScores[position].score.unusedAreas = (-1) * score
            ScoreCategory.PREMIUM_AREAS -> _playersScores[position].score.premiumAreas = score
            ScoreCategory.GOLD -> _playersScores[position].score.gold = score
            ScoreCategory.BEG -> _playersScores[position].score.begs = (-3) * score
        }

        this.playersScores.value = _playersScores
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
        val sortedPlayersScores = _playersScores.sortedByDescending { it.score.total() }
        sortedPlayersScores.forEachIndexed { index, playerScore ->
            playerScore.score.place = index + 1
        }
    }

    private fun sortScores() {
        _playersScores.sortBy { it.score.place }
        playersScores.postValue(_playersScores)
    }

    fun submitScores() {
        saveScoresToFireStore()
    }

    private fun saveScoresToFireStore() {

        for (playerScore in _playersScores)
            fireStoreRepository.addScore(playerScore.score, object : FireStoreRepository.UploadScoreListener {
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
        const val FAILED_TO_UPLOAD_SCORE = "Failed to submit playersScores"
    }
}