package com.tomaszkopacz.kawernaapp.ui.main.board

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.managers.AccountManager
import com.tomaszkopacz.kawernaapp.managers.UserManager
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userManager: UserManager,
    private val accountManager: AccountManager
) : ViewModel() {

    private var mUserScores = ArrayList<Score>()

    var userScores = MutableLiveData<ArrayList<Score>>()
    var state = MutableLiveData<String>()

    init {
        downloadScores()
    }

    private fun downloadScores() {
        if (userManager.isUserLoggedIn()) {
            accountManager.getUsersScores(userManager.getLoggedUser()!!,
                object : AccountManager.ScoresListener {
                    override fun onSuccess(scores: ArrayList<Score>, message: Message) {
                        mUserScores = scores
                        exposeScores()

                        state.postValue(message.text)
                    }

                    override fun onFailure(message: Message) {
                        state.postValue(message.text)
                    }
                })
        }
    }

    fun scoreChosen(score: Score) {
        accountManager.setScoreChosen(score)
    }

    private fun exposeScores() {
        userScores.postValue(mUserScores)
    }
}