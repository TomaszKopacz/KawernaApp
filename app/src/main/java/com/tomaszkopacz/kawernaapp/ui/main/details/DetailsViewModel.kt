package com.tomaszkopacz.kawernaapp.ui.main.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.managers.AccountManager
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    accountManager: AccountManager
) : ViewModel() {

    val score = MutableLiveData<Score>()

    init {
        exposeScore(accountManager.getScoreChosen()!!)
    }

    private fun exposeScore(score: Score) {
        this.score.postValue(score)
    }
}