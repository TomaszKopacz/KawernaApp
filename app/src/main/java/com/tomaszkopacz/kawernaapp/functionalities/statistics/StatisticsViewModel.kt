package com.tomaszkopacz.kawernaapp.functionalities.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository

class StatisticsViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private var loggedUser: Player? = null

    private var currentCategory: ScoreCategory = ScoreCategory.TOTAL

    var maxScore = MutableLiveData<Int>()
    var meanScore = MutableLiveData<Int>()

    fun init() {
        getLoggedUser()
    }

    private fun getLoggedUser() {
        loggedUser = sharedPrefsRepository.getLoggedUser()!!
        categoryChanged(ScoreCategory.TOTAL)
    }

    fun categoryChanged(category: ScoreCategory) {
        currentCategory = category

        countMax(category)
        countMean(category)
    }

    private fun countMax(category: ScoreCategory) {
        fireStoreRepository.getPlayerScores(loggedUser!!.email, object : FireStoreRepository.DownloadScoresListener {
            override fun onSuccess(scores: ArrayList<Score>) {

                val maxValue = when (category) {
                    ScoreCategory.TOTAL -> scores.maxBy { it.total() }!!.total()
                    ScoreCategory.ANIMALS -> scores.maxBy { it.livestock }!!.livestock
                    ScoreCategory.CEREAL -> scores.maxBy { it.cereal }!!.cereal
                    ScoreCategory.VEGETABLES -> scores.maxBy { it.vegetables }!!.vegetables
                    ScoreCategory.AREAS -> scores.maxBy { it.areas }!!.areas
                    ScoreCategory.PREMIUM_AREAS -> scores.maxBy { it.premiumAreas }!!.premiumAreas
                    ScoreCategory.GOLD -> scores.maxBy { it.gold }!!.gold
                    else -> 0
                }

                maxScore.postValue(maxValue)
            }

            override fun onFailure(exception: Exception) {

            }
        })
    }

    private fun countMean(category: ScoreCategory) {
        fireStoreRepository.getPlayerScores(loggedUser!!.email, object : FireStoreRepository.DownloadScoresListener {
            override fun onSuccess(scores: ArrayList<Score>) {
                val mean = scores.sumBy { score ->
                    when (category) {
                        ScoreCategory.TOTAL -> score.total()
                        ScoreCategory.ANIMALS -> score.livestock
                        ScoreCategory.CEREAL -> score.cereal
                        ScoreCategory.VEGETABLES -> score.vegetables
                        ScoreCategory.AREAS -> score.areas
                        ScoreCategory.PREMIUM_AREAS -> score.premiumAreas
                        ScoreCategory.GOLD -> score.gold
                        else -> 0
                    }
                } / scores.size

                meanScore.postValue(mean)
            }

            override fun onFailure(exception: Exception) {

            }

        })
    }
}