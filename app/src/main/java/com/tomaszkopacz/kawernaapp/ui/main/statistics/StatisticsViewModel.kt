package com.tomaszkopacz.kawernaapp.ui.main.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.data.StatisticsResult
import com.tomaszkopacz.kawernaapp.managers.AccountManager
import com.tomaszkopacz.kawernaapp.managers.StatisticsManager
import com.tomaszkopacz.kawernaapp.managers.UserManager
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val userManager: UserManager,
    private val accountManager: AccountManager,
    private var statisticsManager: StatisticsManager
) : ViewModel() {

    var result = MutableLiveData<StatisticsResult>()

    var state = MutableLiveData<String>()

    init {
        downloadUsersScores()
    }

    private fun downloadUsersScores() {
        state.postValue(Message.SCORES_DOWNLOAD_IN_PROGRESS)

        accountManager.getUsersScores(userManager.getLoggedUser()!!,
            object : AccountManager.ScoresListener {

                override fun onSuccess(scores: ArrayList<Score>, message: Message) {
                    statisticsManager.setScores(scores)
                    process(ScoreCategory.ANIMALS)

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
        exposeResult(statisticsManager.calculateResults(category))
    }

    private fun exposeResult(result: StatisticsResult) {
        this.result.postValue(result)
    }
}