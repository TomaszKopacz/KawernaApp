package com.tomaszkopacz.kawernaapp.functionalities.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.extensions.isEmailPattern
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository

class HomeViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private var mUserScores = ArrayList<Score>()

    var userScores = MutableLiveData<ArrayList<Score>>()
    var state = MutableLiveData<String>()

    init {
        state.value = STATE_NONE
    }

    fun downloadScores() {
        val user = sharedPrefsRepository.getLoggedUser()!!.email

        when {
            user == null ->
                scoresListener.onFailure(Exception("No user is logged in!"))

            user.isEmpty() ->
                scoresListener.onFailure(Exception("User has unset  email!"))

            !user.isEmailPattern() ->
                scoresListener.onFailure(Exception("User's email is incorrect!"))

            else ->
                fireStoreRepository.getPlayerScores(user, scoresListener)
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