package com.tomaszkopacz.kawernaapp.ui.main.statistics

import android.util.Log
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory

class StatisticsCalculator(private val scores: ArrayList<Score>) {

    fun maxTotal(): Int {
        return if (scores.isNotEmpty()) {
            scores.maxBy { it.total() }!!.total()

        } else {
            0
        }
    }

    fun worstTotal(): Int {
        return if (scores.isNotEmpty()) {
            scores.minBy { it.total() }!!.total()

        } else {
            0
        }
    }

    fun meanTotal(): Int {
        return if (scores.isNotEmpty()) {
            scores.sumBy { it.total() } / scores.size

        } else {
            0
        }
    }

    fun moreThan100PointsCount(): Int {
        var count = 0
        for (score in scores) {
            if (score.total() >= 100) {
                count++
            }
        }
        return count
    }

    fun winCount(): Int {
        var count = 0
        for (score in scores) {
            if (score.place == 1) {
                count++
            }
        }
        return count
    }

    fun gamesCount(): Int {
        return scores.size
    }

    fun seriesTotal(): LineGraphSeries<DataPoint> {
        val series = LineGraphSeries<DataPoint>()

        for (score in scores.reversed().withIndex()) {
            val dataPoint = DataPoint(score.index.toDouble(), score.value.total().toDouble())
            series.appendData(dataPoint, true, 1000)
        }

        return series
    }

    fun maxCategoryResult(category: ScoreCategory): Int {

        if (scores.isNotEmpty()) {
            return when (category) {
                ScoreCategory.ANIMALS -> scores.maxBy { it.livestock }!!.livestock
                ScoreCategory.ANIMALS_LACK -> scores.maxBy { it.livestockLack }!!.livestockLack
                ScoreCategory.CEREAL -> scores.maxBy { it.cereal }!!.cereal
                ScoreCategory.VEGETABLES -> scores.maxBy { it.vegetables }!!.vegetables
                ScoreCategory.RUBIES -> scores.maxBy { it.rubies }!!.rubies
                ScoreCategory.DWARFS -> scores.maxBy { it.dwarfs }!!.dwarfs
                ScoreCategory.UNUSED_AREAS -> scores.maxBy { it.unusedAreas }!!.unusedAreas
                ScoreCategory.AREAS -> scores.maxBy { it.areas }!!.areas
                ScoreCategory.PREMIUM_AREAS -> scores.maxBy { it.premiumAreas }!!.premiumAreas
                ScoreCategory.GOLD -> scores.maxBy { it.gold }!!.gold
            }
        } else {
            return 0
        }
    }

    fun meanCategoryResult(category: ScoreCategory): Int {
        if (scores.isNotEmpty()) {
            return scores.sumBy { score ->
                when (category) {
                    ScoreCategory.ANIMALS -> score.livestock
                    ScoreCategory.ANIMALS_LACK -> score.livestockLack
                    ScoreCategory.CEREAL -> score.cereal
                    ScoreCategory.VEGETABLES -> score.vegetables
                    ScoreCategory.RUBIES -> score.rubies
                    ScoreCategory.DWARFS -> score.dwarfs
                    ScoreCategory.UNUSED_AREAS -> score.unusedAreas
                    ScoreCategory.AREAS -> score.areas
                    ScoreCategory.PREMIUM_AREAS -> score.premiumAreas
                    ScoreCategory.GOLD -> score.gold
                }
            } / scores.size
        } else {
            return 0
        }
    }

    fun seriesForCategory(category: ScoreCategory): LineGraphSeries<DataPoint> {
        val series = LineGraphSeries<DataPoint>()

        for (score in scores.reversed().withIndex()) {

            val value = when (category) {
                ScoreCategory.ANIMALS -> score.value.livestock
                ScoreCategory.ANIMALS_LACK -> score.value.livestockLack
                ScoreCategory.CEREAL -> score.value.cereal
                ScoreCategory.VEGETABLES -> score.value.vegetables
                ScoreCategory.RUBIES -> score.value.rubies
                ScoreCategory.DWARFS -> score.value.dwarfs
                ScoreCategory.UNUSED_AREAS -> score.value.unusedAreas
                ScoreCategory.AREAS -> score.value.areas
                ScoreCategory.PREMIUM_AREAS -> score.value.premiumAreas
                ScoreCategory.GOLD -> score.value.gold
            }

            val dataPoint = DataPoint(score.index.toDouble(), value.toDouble())
            series.appendData(dataPoint, true, scores.size + 1)
        }

        return series
    }
}