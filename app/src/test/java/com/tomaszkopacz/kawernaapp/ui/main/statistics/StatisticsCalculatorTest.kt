package com.tomaszkopacz.kawernaapp.ui.main.statistics

import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class StatisticsCalculatorTest {

    private lateinit var calculator: StatisticsCalculator

    private val someScores = ArrayList<Score>()
    private val sameScores = ArrayList<Score>()
    private val emptyScores = ArrayList<Score>()
    private val highScores = ArrayList<Score>()

    @Before
    fun setUp() {
        populateScores()
    }

    private fun populateScores() {

        populateSomeScores()
        populateSameScores()
        populateHighScores()
    }

    private fun populateSomeScores() {
        var score: Score
        for (i in 1..3) {
            score = Score("player", "game", "date")
            score.livestock = i
            score.livestockLack = 0
            score.cereal = i
            score.vegetables = i
            score.rubies = i
            score.dwarfs = 3
            score.unusedAreas = 0
            score.areas = i
            score.premiumAreas = i
            score.gold = i
            score.place = i

            someScores.add(score)
        }
    }

    private fun populateSameScores() {
        val score = Score("player", "game", "date")
        score.livestock = 1
        score.livestockLack = 0
        score.cereal = 1
        score.vegetables = 1
        score.rubies = 1
        score.dwarfs = 3
        score.unusedAreas = 0
        score.areas = 1
        score.gold = 1
        score.place = 1

        for (i in 1..3) {
            sameScores.add(score)
        }
    }

    private fun populateHighScores() {
        var score: Score
        for (i in 1..3) {
            score = Score("player", "game", "date")
            score.livestock = i*10
            score.livestockLack = 0
            score.cereal = i*10
            score.vegetables = i*10
            score.rubies = i*10
            score.dwarfs = 3
            score.unusedAreas = 0
            score.areas = i*10
            score.gold = i*10

            highScores.add(score)
        }
    }

    @Test
    fun `maxTotal() - zero is returned for empty scores set`() {
        calculator = StatisticsCalculator(emptyScores)

        val maxTotal = calculator.maxTotal()
        assertThat(maxTotal, `is`(0))
    }

    @Test
    fun `maxTotal() - correct max value is returned for sample scores set`() {
        calculator = StatisticsCalculator(someScores)

        val maxTotal = calculator.maxTotal()
        assertThat(maxTotal, `is`(23))
    }

    @Test
    fun `maxTotal() - correct max value is returned for same scores`() {
        calculator = StatisticsCalculator(sameScores)

        val maxTotal = calculator.maxTotal()
        assertThat(maxTotal, `is`(9))
    }

    @Test
    fun `worstTotal() - zero is returned for empty scores set`() {
        calculator = StatisticsCalculator(emptyScores)

        val minTotal = calculator.worstTotal()
        assertThat(minTotal, `is`(0))
    }

    @Test
    fun `worstTotal() - correct min value is returned for sample scores set`() {
        calculator = StatisticsCalculator(someScores)

        val minTotal = calculator.worstTotal()
        assertThat(minTotal, `is`(10))
    }

    @Test
    fun `worstTotal() - correct min value is returned for same scores`() {
        calculator = StatisticsCalculator(sameScores)

        val minTotal = calculator.worstTotal()
        assertThat(minTotal, `is`(9))
    }

    @Test
    fun `meanTotal() - zero is returned for empty scores set`() {
        calculator = StatisticsCalculator(emptyScores)

        val meanTotal = calculator.meanTotal()
        assertThat(meanTotal, `is`(0))
    }

    @Test
    fun `meanTotal() - correct mean value is returned for sample scores set`() {
        calculator = StatisticsCalculator(someScores)

        val meanTotal = calculator.meanTotal()
        assertThat(meanTotal, `is`(16))
    }

    @Test
    fun `meanTotal() - correct mean value is returned for same scores`() {
        calculator = StatisticsCalculator(sameScores)

        val meanTotal = calculator.meanTotal()
        assertThat(meanTotal, `is`(9))
    }

    @Test
    fun `moreThan100PointsCount() - zero is returned for empty scores set`() {
        calculator = StatisticsCalculator(emptyScores)

        val moreThan100Points = calculator.moreThan100PointsCount()
        assertThat(moreThan100Points, `is`(0))
    }

    @Test
    fun `moreThan100PointsCount() - correct value is returned for sample scores`() {
        calculator = StatisticsCalculator(someScores)

        val moreThan100Points = calculator.moreThan100PointsCount()
        assertThat(moreThan100Points, `is`(0))
    }

    @Test
    fun `moreThan100PointsCount() - correct value is returned for high scores`() {
        calculator = StatisticsCalculator(highScores)

        val moreThan100Points = calculator.moreThan100PointsCount()
        assertThat(moreThan100Points, `is`(2))
    }

    @Test
    fun `winCount() - zero is returned for empty scores set`() {
        calculator = StatisticsCalculator(emptyScores)

        val winCount = calculator.winCount()
        assertThat(winCount, `is`(0))
    }

    @Test
    fun `winCount() - correct value is returned for sample scores set`() {
        calculator = StatisticsCalculator(someScores)

        val winCount = calculator.winCount()
        assertThat(winCount, `is`(1))
    }

    @Test
    fun `winCount() - correct value is returned for same scores`() {
        calculator = StatisticsCalculator(sameScores)

        val winCount = calculator.winCount()
        assertThat(winCount, `is`(3))
    }

    @Test
    fun `gamesCount() - zero is returned for empty scores set`() {
        calculator = StatisticsCalculator(emptyScores)

        val gamesCount = calculator.gamesCount()
        assertThat(gamesCount, `is`(0))
    }

    @Test
    fun `gamesCount() - correct value is returned for sample scores set`() {
        calculator = StatisticsCalculator(someScores)

        val gamesCount = calculator.gamesCount()
        assertThat(gamesCount, `is`(3))
    }

    @Test
    fun `maxCategoryResult() - zero is returned for empty scores set`() {
        calculator = StatisticsCalculator(emptyScores)

        val maxAnimals = calculator.maxCategoryResult(ScoreCategory.ANIMALS)
        val maxAnimalsLack = calculator.maxCategoryResult(ScoreCategory.ANIMALS_LACK)
        val maxCereals = calculator.maxCategoryResult(ScoreCategory.CEREAL)
        val maxVegetables = calculator.maxCategoryResult(ScoreCategory.VEGETABLES)
        val maxDwarfs = calculator.maxCategoryResult(ScoreCategory.DWARFS)
        val maxRubies = calculator.maxCategoryResult(ScoreCategory.RUBIES)
        val maxUnusedAreas = calculator.maxCategoryResult(ScoreCategory.UNUSED_AREAS)
        val maxAreas = calculator.maxCategoryResult(ScoreCategory.AREAS)
        val maxPremiumAreas = calculator.maxCategoryResult(ScoreCategory.PREMIUM_AREAS)
        val maxGold = calculator.maxCategoryResult(ScoreCategory.GOLD)

        assertThat(maxAnimals, `is`(0))
        assertThat(maxAnimalsLack, `is`(0))
        assertThat(maxCereals, `is`(0))
        assertThat(maxVegetables, `is`(0))
        assertThat(maxDwarfs, `is`(0))
        assertThat(maxRubies, `is`(0))
        assertThat(maxUnusedAreas, `is`(0))
        assertThat(maxAreas, `is`(0))
        assertThat(maxPremiumAreas, `is`(0))
        assertThat(maxGold, `is`(0))
    }

    @Test
    fun `maxCategoryResult() - correct value is returned for sample scores set`() {
        calculator = StatisticsCalculator(someScores)

        val maxAnimals = calculator.maxCategoryResult(ScoreCategory.ANIMALS)
        val maxAnimalsLack = calculator.maxCategoryResult(ScoreCategory.ANIMALS_LACK)
        val maxCereals = calculator.maxCategoryResult(ScoreCategory.CEREAL)
        val maxVegetables = calculator.maxCategoryResult(ScoreCategory.VEGETABLES)
        val maxDwarfs = calculator.maxCategoryResult(ScoreCategory.DWARFS)
        val maxRubies = calculator.maxCategoryResult(ScoreCategory.RUBIES)
        val maxUnusedAreas = calculator.maxCategoryResult(ScoreCategory.UNUSED_AREAS)
        val maxAreas = calculator.maxCategoryResult(ScoreCategory.AREAS)
        val maxPremiumAreas = calculator.maxCategoryResult(ScoreCategory.PREMIUM_AREAS)
        val maxGold = calculator.maxCategoryResult(ScoreCategory.GOLD)

        assertThat(maxAnimals, `is`(3))
        assertThat(maxAnimalsLack, `is`(0))
        assertThat(maxCereals, `is`(3))
        assertThat(maxVegetables, `is`(3))
        assertThat(maxDwarfs, `is`(3))
        assertThat(maxRubies, `is`(3))
        assertThat(maxUnusedAreas, `is`(0))
        assertThat(maxAreas, `is`(3))
        assertThat(maxPremiumAreas, `is`(3))
        assertThat(maxGold, `is`(3))
    }

    @Test
    fun `meanCategoryResult() - zero is returned for empty scores set`() {
        calculator = StatisticsCalculator(emptyScores)

        val meanAnimals = calculator.meanCategoryResult(ScoreCategory.ANIMALS)
        val meanAnimalsLack = calculator.meanCategoryResult(ScoreCategory.ANIMALS_LACK)
        val meanCereals = calculator.meanCategoryResult(ScoreCategory.CEREAL)
        val meanVegetables = calculator.meanCategoryResult(ScoreCategory.VEGETABLES)
        val meanDwarfs = calculator.meanCategoryResult(ScoreCategory.DWARFS)
        val meanRubies = calculator.meanCategoryResult(ScoreCategory.RUBIES)
        val meanUnusedAreas = calculator.meanCategoryResult(ScoreCategory.UNUSED_AREAS)
        val meanAreas = calculator.meanCategoryResult(ScoreCategory.AREAS)
        val meanPremiumAreas = calculator.meanCategoryResult(ScoreCategory.PREMIUM_AREAS)
        val meanGold = calculator.meanCategoryResult(ScoreCategory.GOLD)

        assertThat(meanAnimals, `is`(0))
        assertThat(meanAnimalsLack, `is`(0))
        assertThat(meanCereals, `is`(0))
        assertThat(meanVegetables, `is`(0))
        assertThat(meanDwarfs, `is`(0))
        assertThat(meanRubies, `is`(0))
        assertThat(meanUnusedAreas, `is`(0))
        assertThat(meanAreas, `is`(0))
        assertThat(meanPremiumAreas, `is`(0))
        assertThat(meanGold, `is`(0))
    }

    @Test
    fun `meanCategoryResult() - correct value is returned for sample scores set`() {
        calculator = StatisticsCalculator(someScores)

        val meanAnimals = calculator.meanCategoryResult(ScoreCategory.ANIMALS)
        val meanAnimalsLack = calculator.meanCategoryResult(ScoreCategory.ANIMALS_LACK)
        val meanCereals = calculator.meanCategoryResult(ScoreCategory.CEREAL)
        val meanVegetables = calculator.meanCategoryResult(ScoreCategory.VEGETABLES)
        val meanDwarfs = calculator.meanCategoryResult(ScoreCategory.DWARFS)
        val meanRubies = calculator.meanCategoryResult(ScoreCategory.RUBIES)
        val meanUnusedAreas = calculator.meanCategoryResult(ScoreCategory.UNUSED_AREAS)
        val meanAreas = calculator.meanCategoryResult(ScoreCategory.AREAS)
        val meanPremiumAreas = calculator.meanCategoryResult(ScoreCategory.PREMIUM_AREAS)
        val meanGold = calculator.meanCategoryResult(ScoreCategory.GOLD)

        assertThat(meanAnimals, `is`(2))
        assertThat(meanAnimalsLack, `is`(0))
        assertThat(meanCereals, `is`(2))
        assertThat(meanVegetables, `is`(2))
        assertThat(meanDwarfs, `is`(3))
        assertThat(meanRubies, `is`(2))
        assertThat(meanUnusedAreas, `is`(0))
        assertThat(meanAreas, `is`(2))
        assertThat(meanPremiumAreas, `is`(2))
        assertThat(meanGold, `is`(2))
    }
}