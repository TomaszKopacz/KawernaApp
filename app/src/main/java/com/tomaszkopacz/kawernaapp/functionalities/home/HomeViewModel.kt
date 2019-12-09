package com.tomaszkopacz.kawernaapp.functionalities.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Score

class HomeViewModel : ViewModel() {

    private var mUserScores = ArrayList<Score>()

    var userScores = MutableLiveData<ArrayList<Score>>()
    var state = MutableLiveData<String>()

    init {
        state.value = STATE_NONE
    }

    fun downloadScores() {
        when (AuthManager.getLoggedUser()?.email) {
            "tk@op.pl" -> FireStoreRepository().getPlayerScores("Tomasz", scoresListener)
            "arek@op.pl" -> FireStoreRepository().getPlayerScores("Arek", scoresListener)
        }
    }

    private val scoresListener = object : FireStoreRepository.DownloadScoresListener {
        override fun onSuccess(scores: ArrayList<Score>) {
            mUserScores = scores

            userScores.value = mUserScores
            state.value = STATE_SCORES_DOWNLOADED
        }

        override fun onFailure(exception: Exception) {
            state.value = STATE_SCORES_DOWNLOAD_FAILED
        }
    }

    companion object {
        const val STATE_NONE = "NONE"
        const val STATE_SCORES_DOWNLOADED = "SCORES DOWNLOADED"
        const val STATE_SCORES_DOWNLOAD_FAILED = "SCORES DOWNLOAD FAILED"
    }
}