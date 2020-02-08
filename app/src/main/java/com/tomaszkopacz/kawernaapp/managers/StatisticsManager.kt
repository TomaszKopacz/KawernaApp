package com.tomaszkopacz.kawernaapp.managers

import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.data.StatisticsResult
import com.tomaszkopacz.kawernaapp.di.ActivityScope
import com.tomaszkopacz.kawernaapp.ui.main.statistics.StatisticsCalculator
import javax.inject.Inject

@ActivityScope
class StatisticsManager @Inject constructor() {

    private var scores = ArrayList<Score>()

    fun setScores(scores: ArrayList<Score>) {
        this.scores = scores
    }

    fun calculateResults(category: ScoreCategory): StatisticsResult {
        val calculator = StatisticsCalculator(scores)
        val result = StatisticsResult()

        result.maxTotal = calculator.maxTotal()
        result.worstTotal = calculator.worstTotal()
        result.meanTotal = calculator.meanTotal()
        result.bestScoresCount = calculator.moreThan100PointsCount()
        result.winsCount = calculator.winCount()
        result.gamesCount = calculator.gamesCount()
        result.seriesTotal = calculator.seriesTotal()

        result.maxForCategory = calculator.maxCategoryResult(category)
        result.meanForCategory = calculator.meanCategoryResult(category)
        result.seriesForCategory = calculator.seriesForCategory(category)

        return result
    }
}