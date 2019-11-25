package com.tomaszkopacz.kawernaapp.functionalities.newgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Categories
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score

class PlayersScoresViewModel : ViewModel() {

    var currentCategory: MutableLiveData<Int> = MutableLiveData()

    private var _scores: ArrayList<Score> = ArrayList()
    var scores: MutableLiveData<ArrayList<Score>> = MutableLiveData()

    init {
        currentCategory.value = Categories.LIVESTOCK
    }

    fun setPlayers(players: ArrayList<Player>) {
        for (player in players)
            this._scores.add(Score(player))

        this.scores.value = _scores
    }

    fun updateCurrentScore(position: Int, score: Int) {
        when (currentCategory.value) {
            Categories.LIVESTOCK -> _scores[position].livestock = score
            Categories.LIVESTOCK_LACK -> _scores[position].livestockLack = (-2)*score
            Categories.CEREAL -> _scores[position].cereal = score/2 + score%2
            Categories.VEGETABLES -> _scores[position].vegetables = score
            Categories.RUBIES -> _scores[position].rubies = score
            Categories.DWARFS -> _scores[position].dwarfs = score
            Categories.AREAS -> _scores[position].areas = score
            Categories.UNUSED_AREAS -> _scores[position].unusedAreas = (-1)*score
            Categories.PREMIUM_AREAS -> _scores[position].premiumAreas = score
            Categories.GOLD -> _scores[position].gold = score
            Categories.BEGS -> _scores[position].begs = (-3)*score
        }

        this.scores.value = _scores
    }

    fun previousCategory(){
        currentCategory.value = currentCategory.value!!.dec()
        sortScores()
    }

    fun nextCategory() {
        currentCategory.value = currentCategory.value!!.inc()
        sortScores()
    }

    private fun sortScores() {
        _scores.sortByDescending { it.total() }
        scores.value = _scores
    }

    fun submitScores() {

    }
}