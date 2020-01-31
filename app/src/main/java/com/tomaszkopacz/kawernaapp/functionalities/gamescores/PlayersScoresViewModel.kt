package com.tomaszkopacz.kawernaapp.functionalities.gamescores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.*
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlayersScoresViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    var state: MutableLiveData<String> = MutableLiveData()

    private var _playersScores: ArrayList<PlayerScore> = ArrayList()
    var playersScores: MutableLiveData<ArrayList<PlayerScore>> = MutableLiveData()

    var currentCategory: MutableLiveData<ScoreCategory> = MutableLiveData()

    init {
        resetState()
        initGame()
        exposeScores()
        exposeCategory(ScoreCategory.ANIMALS)
    }

    private fun initGame() {
        val gameId = sharedPrefsRepository.getGameId()
        val players = sharedPrefsRepository.getPlayers()
        populatePlayersScores(gameId!!, players!!)
    }

    private fun populatePlayersScores(gameId: String, players: ArrayList<Player>) {
        val currentDate = getCurrentDateString()
        val playersCount = players.size

        for (player in players)
            this._playersScores.add(PlayerScore(player, Score(player.email, gameId, currentDate, playersCount)))
    }

    private fun getCurrentDateString(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun updateCurrentScoresResults(position: Int, score: Int) {
        when (currentCategory.value) {
            ScoreCategory.TOTAL -> { }
            ScoreCategory.ANIMALS -> _playersScores[position].score.livestock = score
            ScoreCategory.ANIMALS_LACK -> _playersScores[position].score.livestockLack = score
            ScoreCategory.CEREAL -> _playersScores[position].score.cereal = score
            ScoreCategory.VEGETABLES -> _playersScores[position].score.vegetables = score
            ScoreCategory.RUBIES -> _playersScores[position].score.rubies = score
            ScoreCategory.DWARFS -> _playersScores[position].score.dwarfs = score
            ScoreCategory.AREAS -> _playersScores[position].score.areas = score
            ScoreCategory.UNUSED_AREAS -> _playersScores[position].score.unusedAreas = score
            ScoreCategory.PREMIUM_AREAS -> _playersScores[position].score.premiumAreas = score
            ScoreCategory.GOLD -> _playersScores[position].score.gold = score
        }

        this.playersScores.value = _playersScores
    }

    fun previousCategory() {
        val currentCategoryIndex = currentCategory.value!!.ordinal
        exposeCategory(ScoreCategory.values()[currentCategoryIndex.dec()])

        sortScores()
    }

    fun nextCategory() {
        val currentCategoryIndex = currentCategory.value!!.ordinal
        exposeCategory(ScoreCategory.values()[currentCategoryIndex.inc()])

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
        exposeScores()
    }

    fun submitScores() {
        updateUsersPlaces()
        sortScores()
        saveScoresToFireStore()
    }

    private fun saveScoresToFireStore() {
        for (playerScore in _playersScores)
            fireStoreRepository.addScore(  playerScore.score, fireStoreListener)
    }

    private var fireStoreListener = object : FireStoreRepository.UploadScoreListener {
        override fun onSuccess(score: Score) {
            scoresUploadSucceed()
        }

        override fun onFailure(exception: java.lang.Exception) {
            scoresUploadFailed()
        }
    }

    private fun exposeScores() {
        playersScores.postValue(_playersScores)
    }

    private fun exposeCategory(category: ScoreCategory) {
        currentCategory.postValue(category)
    }

    private fun resetState() {
        state.postValue(STATE_NONE)
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