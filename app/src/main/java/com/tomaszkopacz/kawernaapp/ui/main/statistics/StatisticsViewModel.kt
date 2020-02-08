package com.tomaszkopacz.kawernaapp.ui.main.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.managers.AccountManager
import com.tomaszkopacz.kawernaapp.managers.UserManager
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
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
        state.postValue(Message.SCORES_DOWNLOAD_IN_PROGRESS)

        accountManager.getUsersScores(userManager.getLoggedUser()!!,
            object : AccountManager.ScoresListener {

                override fun onSuccess(scores: ArrayList<Score>, message: Message) {
                    userScores = scores
                    process(ScoreCategory.TOTAL)

                    state.postValue(Message.SCORES_DOWNLOADED)
                }

                override fun onFailure(message: Message) {
                    state.postValue(Message.SCORES_DOWNLOAD_FAILED)
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
}