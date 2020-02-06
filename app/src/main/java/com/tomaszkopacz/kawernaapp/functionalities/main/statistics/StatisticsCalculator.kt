package com.tomaszkopacz.kawernaapp.functionalities.main.statistics

import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory

class StatisticsCalculator(private val scores: ArrayList<Score>) {

//    fun maxTotal(): Int {
//
//    }

    fun maxCategoryResult(category: ScoreCategory): Int {
        return when (category) {
            ScoreCategory.TOTAL -> scores.maxBy { it.total() }!!.total()
            ScoreCategory.ANIMALS -> scores.maxBy { it.livestock }!!.livestock
            ScoreCategory.CEREAL -> scores.maxBy { it.cereal }!!.cereal
            ScoreCategory.VEGETABLES -> scores.maxBy { it.vegetables }!!.vegetables
            ScoreCategory.AREAS -> scores.maxBy { it.areas }!!.areas
            ScoreCategory.PREMIUM_AREAS -> scores.maxBy { it.premiumAreas }!!.premiumAreas
            ScoreCategory.GOLD -> scores.maxBy { it.gold }!!.gold
            else -> 0
        }
    }

    fun meanCategoryResult(category: ScoreCategory): Int {
        return scores.sumBy { score ->
            when (category) {
                ScoreCategory.TOTAL -> score.total()
                ScoreCategory.ANIMALS -> score.livestock
                ScoreCategory.CEREAL -> score.cereal
                ScoreCategory.VEGETABLES -> score.vegetables
                ScoreCategory.AREAS -> score.areas
                ScoreCategory.PREMIUM_AREAS -> score.premiumAreas
                ScoreCategory.GOLD -> score.gold
                else -> 0
            }
        } / scores.size
    }
}