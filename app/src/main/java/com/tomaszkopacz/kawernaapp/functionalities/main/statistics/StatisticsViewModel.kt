package com.tomaszkopacz.kawernaapp.functionalities.main.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.scores.AccountManager
import com.tomaszkopacz.kawernaapp.user.UserManager
import java.lang.Exception

class StatisticsViewModel(
    private val userManager: UserManager,
    private val accountManager: AccountManager
) : ViewModel() {

    private var userScores = ArrayList<Score>()

    var maxScore = MutableLiveData<Int>()
    var meanScore = MutableLiveData<Int>()

    var state = MutableLiveData<String>()

    init {
        downloadUsersScores()
    }

    private fun downloadUsersScores() {
        downloadInProgress()

        accountManager.getUsersScores(userManager.getLoggedUser()!!,
            object : AccountManager.ScoresListener {

                override fun onSuccess(scores: ArrayList<Score>) {
                    userScores = scores
                    process(ScoreCategory.TOTAL)

                    downloadSucceed()
                }

                override fun onFailure(exception: Exception) {
                    downloadFailed()
                }
            })
    }

    fun categoryChanged(category: ScoreCategory) {
        process(category)
    }

    private fun process(category: ScoreCategory) {
        val calculator = StatisticsCalculator(userScores)
        val maxScore = calculator.maxCategoryResult(category)
        val meanScore = calculator.meanCategoryResult(category)

        exposeMaxScore(maxScore)
        exposeMeanScore(meanScore)
    }

    private fun exposeMaxScore(value: Int) {
        maxScore.postValue(value)
    }

    private fun exposeMeanScore(value: Int) {
        meanScore.postValue(value)
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

    companion object {
        const val STATE_SCORES_DOWNLOAD_IN_PROGRESS = "SCORES DOWNLOAD IN PROGRESS"
        const val STATE_SCORES_DOWNLOAD_SUCCEED = "SCORES DOWNLOAD SUCCEED"
        const val STATE_SCORES_DOWNLOAD_FAILED = "SCORES DOWNLOAD FAILED"
    }
}