package com.tomaszkopacz.kawernaapp.ui.main.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.data.StatisticsResult
import com.tomaszkopacz.kawernaapp.managers.AccountManager
import com.tomaszkopacz.kawernaapp.managers.StatisticsManager
import com.tomaszkopacz.kawernaapp.managers.UserManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val userManager: UserManager,
    private val accountManager: AccountManager,
    private var statisticsManager: StatisticsManager
) : ViewModel() {

    var result = MutableLiveData<StatisticsResult>()

    var state = MutableLiveData<String>()

    init {
        GlobalScope.launch {
            downloadUsersScores()
        }
    }

    private suspend fun downloadUsersScores() {
        state.postValue(Message.SCORES_DOWNLOAD_IN_PROGRESS)

        when (val result = accountManager.getUsersScores(userManager.getLoggedUser()!!)) {
            is Result.Success -> {
                statisticsManager.setScores(result.data as ArrayList<Score>)
                process(ScoreCategory.ANIMALS)
                state.postValue(Message.SCORES_DOWNLOADED)
            }

            is Result.Failure -> {
                state.postValue(result.message.text)
            }
        }
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