package com.tomaszkopacz.kawernaapp.viemodel.newgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Categories
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score

class PlayersScoresViewModel : ViewModel() {

    var currentCategory: MutableLiveData<Int> = MutableLiveData()

    private var scores: ArrayList<Score> = ArrayList()
    var scoresData: MutableLiveData<ArrayList<Score>> = MutableLiveData()

    init {
        currentCategory.value = Categories.LIVESTOCK
    }

    fun setPlayers(players: ArrayList<Player>) {
        for (player in players)
            this.scores.add(Score(player))

        this.scoresData.value = scores
    }

    fun updateCurrentScore(position: Int, score: Int) {
        when (currentCategory.value) {
            Categories.LIVESTOCK -> scores[position].livestock = score
            Categories.LIVESTOCK_LACK -> scores[position].livestockLack = (-2)*score
            Categories.CEREAL -> scores[position].cereal = score/2 + score%2
            Categories.VEGETABLES -> scores[position].vegetables = score
            Categories.RUBIES -> scores[position].rubies = score
            Categories.DWARFS -> scores[position].dwarfs = score
            Categories.AREAS -> scores[position].areas = score
            Categories.UNUSED_AREAS -> scores[position].unusedAreas = (-1)*score
            Categories.PREMIUM_AREAS -> scores[position].premiumAreas = score
            Categories.GOLD -> scores[position].gold = score
            Categories.BEGS -> scores[position].begs = (-3)*score
        }

        this.scoresData.value = scores
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
        scores.sortByDescending { it.total() }
        scoresData.value = scores
    }

    fun submitScores() {

    }
}