package com.tomaszkopacz.kawernaapp.functionalities.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository

class StatisticsViewModel(
    private val authManager: AuthManager,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private var _category: Int = TOTAL
    val category = MutableLiveData<Int>()

    var maxScore = MutableLiveData<Int>()
    var meanScore = MutableLiveData<Int>()

    fun init() {
        setCategory(TOTAL)
    }

    fun setCategory(category: Int) {
        maxScore.postValue(category)
        meanScore.postValue(60)
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