package com.tomaszkopacz.kawernaapp.functionalities.main.statistics

import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory

class StatisticsCalculator(private val scores: ArrayList<Score>) {

//    fun maxTotal(): Int {
//
//    }

    fun maxCategoryResult(category: ScoreCategory): Int {

        if (scores.isNotEmpty()) {
            return when (category) {
                ScoreCategory.TOTAL -> scores.maxBy { it.total() }!!.total()
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
                    ScoreCategory.TOTAL -> score.total()
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
}