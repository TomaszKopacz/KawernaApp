package com.tomaszkopacz.kawernaapp.functionalities.resultscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Score
import java.lang.Exception

class ResultScreenViewModel : ViewModel() {

    private var mResultScores = ArrayList<Score>()

    var resultScores = MutableLiveData<ArrayList<Score>>()

    fun downloadGameResults(gameId: String) {
        FireStoreRepository().getGameScores(gameId, listener)
    }

    private val listener = object : FireStoreRepository.DownloadScoresListener {
        override fun onSuccess(scores: ArrayList<Score>) {
            mResultScores = scores
            resultScores.value = mResultScores
        }

        override fun onFailure(exception: Exception) {

        }

    }
}