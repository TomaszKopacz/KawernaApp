package com.tomaszkopacz.kawernaapp.functionalities.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import java.lang.Exception

class StatisticsViewModel(
    private val authManager: AuthManager,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private var loggedUser: Player? = null

    private var currentCategory: Int = TOTAL

    var maxScore = MutableLiveData<Int>()
    var meanScore = MutableLiveData<Int>()

    fun init() {
        getLoggedUser()
    }

    private fun getLoggedUser() {
        val email = authManager.getLoggedUser()
        fireStoreRepository.getPlayerByEmail(email!!, object : FireStoreRepository.DownloadPlayerListener {
            override fun onSuccess(player: Player?) { loggedUserFound(player!!) }
            override fun onFailure(exception: Exception) { }
        })
    }

    private fun loggedUserFound(player: Player) {
        loggedUser = player;
        categoryChanged(TOTAL)
    }

    fun categoryChanged(category: Int) {
        currentCategory = category

        countMax(category)
        countMean(category)
    }

    private fun countMax(category: Int) {
        fireStoreRepository.getPlayerScores(loggedUser!!.email, object : FireStoreRepository.DownloadScoresListener {
            override fun onSuccess(scores: ArrayList<Score>) {

                val maxValue = when (category) {
                    TOTAL -> scores.maxBy { it.total() }!!.total()
                    ANIMALS -> scores.maxBy { it.livestock!! }!!.livestock
                    CEREALS -> scores.maxBy { it.cereal!! }!!.cereal
                    VEGETABLES -> scores.maxBy { it.vegetables!! }!!.vegetables
                    AREAS -> scores.maxBy { it.areas!! }!!.areas
                    PREMIUM_AREAS -> scores.maxBy { it.premiumAreas!! }!!.premiumAreas
                    GOLD -> scores.maxBy { it.gold!! }!!.gold
                    else -> 0
                }

                maxScore.postValue(maxValue)
            }

            override fun onFailure(exception: Exception) {

            }
        })
    }

    private fun countMean(category: Int) {
        fireStoreRepository.getPlayerScores(loggedUser!!.email, object : FireStoreRepository.DownloadScoresListener {
            override fun onSuccess(scores: ArrayList<Score>) {
                val mean = scores.sumBy { score ->
                    when (category) {
                        TOTAL -> score.total()
                        ANIMALS -> score.livestock!!
                        CEREALS -> score.cereal!!
                        VEGETABLES -> score.vegetables!!
                        AREAS -> score.areas!!
                        PREMIUM_AREAS -> score.premiumAreas!!
                        GOLD -> score.gold!!
                        else -> 0
                }
                } / scores.size

                meanScore.postValue(mean)
            }

            override fun onFailure(exception: Exception) {

            }

        })
    }

    companion object {
        const val CATEGORY_NUM = 7
        const val TOTAL = 0
        const val ANIMALS = 1
        const val CEREALS = 2
        const val VEGETABLES = 3
        const val AREAS = 4
        const val PREMIUM_AREAS = 5
        const val GOLD = 6
    }
}