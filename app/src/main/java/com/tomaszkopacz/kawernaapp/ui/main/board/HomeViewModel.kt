package com.tomaszkopacz.kawernaapp.ui.main.board

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.managers.AccountManager
import com.tomaszkopacz.kawernaapp.managers.UserManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userManager: UserManager,
    private val accountManager: AccountManager
) : ViewModel() {

    var userScores = MutableLiveData<ArrayList<Score>>()
    var state = MutableLiveData<String>()

    init {
        GlobalScope.launch {
            downloadScores()
        }
    }

    private suspend fun downloadScores() {
        if (userManager.isUserLoggedIn()) {
            when (val result = accountManager.getUsersScores(userManager.getLoggedUser()!!)) {
                is Result.Success -> {
                    userScores.postValue(result.data as ArrayList<Score>)
                    state.postValue(Message.SCORES_DOWNLOADED)
                }

                is Result.Failure -> {
                    state.postValue(result.message.text)
                }
            }
        }
    }

    fun scoreChosen(score: Score) {
        accountManager.setScoreChosen(score)
    }
}