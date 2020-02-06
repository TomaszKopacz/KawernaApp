package com.tomaszkopacz.kawernaapp.functionalities.main.board

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.scores.AccountManager
import com.tomaszkopacz.kawernaapp.user.UserManager

class HomeViewModel(
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
                    override fun onSuccess(scores: ArrayList<Score>) {
                        mUserScores = scores
                        exposeScores()

                        downloadSucceed()
                    }

                    override fun onFailure(exception: Exception) {
                        downloadFailed()
                    }
                })
        }
    }

    private fun exposeScores() {
        userScores.postValue(mUserScores)
    }

    private fun downloadSucceed() {
        state.postValue(STATE_SCORES_DOWNLOADED)
    }

    private fun downloadFailed() {
        state.postValue(STATE_SCORES_DOWNLOAD_FAILED)
    }

    companion object {
        const val STATE_SCORES_DOWNLOADED = "SCORES DOWNLOADED"
        const val STATE_SCORES_DOWNLOAD_FAILED = "SCORES DOWNLOAD FAILED"
    }
}