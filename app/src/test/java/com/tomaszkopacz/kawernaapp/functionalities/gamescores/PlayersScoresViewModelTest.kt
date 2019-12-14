package com.tomaszkopacz.kawernaapp.functionalities.gamescores

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.Categories
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import kotlin.random.Random

class PlayersScoresViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PlayersScoresViewModel
    private lateinit var authManager: AuthManager
    private lateinit var fireStoreRepository: FireStoreRepository

    @Before
    fun setUp() {
        fireStoreRepository = Mockito.mock(FireStoreRepository::class.java)
        authManager = Mockito.mock(AuthManager::class.java)

        viewModel = PlayersScoresViewModel(authManager, fireStoreRepository)
    }

    @Test
    fun `testConstructor - when view model created, then state is NONE` () {
        assertTrue(viewModel.state.value == PlayersScoresViewModel.NONE)
    }

    @Test
    fun `testConstructor - when view model created, then default category is set to LIVESTOCK` () {
        assertTrue(viewModel.currentCategory.value == Categories.LIVESTOCK)
    }

    @Test
    fun `initGame - when players num is too big, then state is TOO_MANY_PLAYERS`() {

    }

    @Test
    fun `initGame - when players num is too low, then state is TOO_LOW_PLAYERS`() {

    }

    @Test
    fun `initGame - given players list, when game initialized, then scores num is equal to players num`() {
        val gameId = "any_id"

        val players = ArrayList<Player>()
        val playersNum = Random.nextInt(10)
        for (i in 1..playersNum) players.add(Player("player$i@gmail.com"))

        viewModel.initGame(gameId, players)

        assertTrue(viewModel.scores.value != null)
        assertTrue(viewModel.scores.value!!.size == playersNum)
    }

    @Test
    fun `initGame - given game ID, when game initialized, then all scores have the same game ID`() {
        val gameId = "unique_id"

        val players = ArrayList<Player>()
        val playersNum = Random.nextInt(10)
        for (i in 1..playersNum) players.add(Player("player$i@gmail.com"))

        viewModel.initGame(gameId, players)

        assertTrue(viewModel.scores.value != null)

        for (score in viewModel.scores.value!!)
            assertTrue(score.game == gameId)
    }

    @Test
    fun `initGame - when game initialized, then all scores have the same date`() {
        val gameId = "any_id"

        val players = ArrayList<Player>()
        val playersNum = Random.nextInt(10)
        for (i in 1..playersNum) players.add(Player("player$i@gmail.com"))

        viewModel.initGame(gameId, players)

        assertTrue(viewModel.scores.value != null)

        val date = viewModel.scores.value!![0].date
        for (score in viewModel.scores.value!!)
            assertTrue(score.date == date)
    }
}