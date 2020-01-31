package com.tomaszkopacz.kawernaapp.functionalities.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository

class StatisticsViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private var userScores = ArrayList<Score>()

    var maxScore = MutableLiveData<Int>()
    var meanScore = MutableLiveData<Int>()

    var state = MutableLiveData<String>()

    init {
        getUsersScores()
    }

    private fun getUsersScores() {
        val user = sharedPrefsRepository.getLoggedUser()
        downloadInProgress()
        fireStoreRepository.getPlayerScores( user!!.email, object : FireStoreRepository.DownloadScoresListener {
                override fun onSuccess(scores: ArrayList<Score>) {
                    userScores = scores
                    categoryChanged(ScoreCategory.TOTAL)
                    downloadSucceed()
                }

                override fun onFailure(exception: java.lang.Exception) {
                    downloadFailed()
                }
            })
    }

    fun categoryChanged(category: ScoreCategory) {
        process(category)
    }

    private fun process(category: ScoreCategory) {
        findMaxScore(category)
        findMeanScore(category)
    }

    private fun findMaxScore(category: ScoreCategory) {
        val maxValue = when (category) {
            ScoreCategory.TOTAL -> userScores.maxBy { it.total() }!!.total()
            ScoreCategory.ANIMALS -> userScores.maxBy { it.livestock }!!.livestock
            ScoreCategory.CEREAL -> userScores.maxBy { it.cereal }!!.cereal
            ScoreCategory.VEGETABLES -> userScores.maxBy { it.vegetables }!!.vegetables
            ScoreCategory.AREAS -> userScores.maxBy { it.areas }!!.areas
            ScoreCategory.PREMIUM_AREAS -> userScores.maxBy { it.premiumAreas }!!.premiumAreas
            ScoreCategory.GOLD -> userScores.maxBy { it.gold }!!.gold
            else -> 0
        }

        showMaxScore(maxValue)
    }

    private fun findMeanScore(category: ScoreCategory) {
        val meanValue = userScores.sumBy { score ->
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
        } / userScores.size

        showMeanScore(meanValue)
    }

    private fun downloadInProgress() {
        state.postValue(STATE_SCORES_DOWNLOAD_IN_PROGRESS)
    }

    private fun downloadSucceed() {
        state.postValue(STATE_SCORES_DOWNLOAD_SUCCEED)
    }

    private fun downloadFailed() {
        state.postValue(STATE_SCORES_DOWNLOAD_SUCCEED)
    }

    private fun showMaxScore(value: Int) {
        maxScore.postValue(value)
    }

    private fun showMeanScore(value: Int) {
        meanScore.postValue(value)
    }

    companion object {
        const val STATE_SCORES_DOWNLOAD_IN_PROGRESS = "SCORES DOWNLOAD IN PROGRESS"
        const val STATE_SCORES_DOWNLOAD_SUCCEED = "SCORES DOWNLOAD SUCCEED"
        const val STATE_SCORES_DOWNLOAD_FAILED = "SCORES DOWNLOAD FAILED"
    }
}