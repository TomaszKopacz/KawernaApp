package com.tomaszkopacz.kawernaapp.functionalities.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Score

class HomeViewModel(
    private val authManager: AuthManager,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private var mUserScores = ArrayList<Score>()

    var userScores = MutableLiveData<ArrayList<Score>>()
    var state = MutableLiveData<String>()

    init {
        state.value = STATE_NONE
    }

    fun downloadScores() {
        when (authManager.getLoggedUser()) {
            "tk@op.pl" -> fireStoreRepository.getPlayerScores("Tomasz", scoresListener)
            "arek@op.pl" -> fireStoreRepository.getPlayerScores("Arek", scoresListener)
            "playernotfound@gmail.com" -> fireStoreRepository.getPlayerScores("playernotfound@gmail.com", scoresListener)
            else -> scoresListener.onFailure(java.lang.Exception("Player is null"))
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