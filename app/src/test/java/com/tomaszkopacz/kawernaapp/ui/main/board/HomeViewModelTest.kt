package com.tomaszkopacz.kawernaapp.ui.main.board

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.managers.AccountManager
import com.tomaszkopacz.kawernaapp.managers.UserManager
import com.tomaszkopacz.kawernaapp.util.extensions.observeOnce
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var userManager: UserManager
    private lateinit var accountManager: AccountManager

    private val player = Player()
    private val scores = ArrayList<Score>()

    @Before
    fun setUp() {
        userManager = Mockito.mock(UserManager::class.java)
        accountManager = Mockito.mock(AccountManager::class.java)
        viewModel = HomeViewModel(userManager, accountManager)

        populateScores()
    }

    private fun populateScores() {
        val score1 = Score("player1", "game1", "date1")
        val score2 = Score("player2", "game2", "date2")
        val score3 = Score("player3", "game3", "date3")

        scores.add(score1)
        scores.add(score2)
        scores.add(score3)
    }

    @Test
    fun `init{} - check state when account service returns failure`() {

        runBlocking {
            Mockito
                .`when`(userManager.isUserLoggedIn())
                .thenReturn(true)

            Mockito
                .`when`(userManager.getLoggedUser())
                .thenReturn(player)

            Mockito
                .`when`(accountManager.getUsersScores(player))
                .thenReturn(Result.Failure(Message("FAILURE")))

            val newViewModel = HomeViewModel(userManager, accountManager)
            newViewModel.state.observeOnce { state ->
                assertThat(state, `is`("FAILURE"))
            }
        }
    }

    @Test
    fun `init{} - check state when scores download succeed`() {

        runBlocking {
            Mockito
                .`when`(userManager.isUserLoggedIn())
                .thenReturn(true)

            Mockito
                .`when`(userManager.getLoggedUser())
                .thenReturn(player)

            Mockito
                .`when`(accountManager.getUsersScores(player))
                .thenReturn(Result.Success(scores))

            val newViewModel = HomeViewModel(userManager, accountManager)
            newViewModel.state.observeOnce { state ->
                assertThat(state, `is`(Message.SCORES_DOWNLOADED))
            }
        }
    }

    @Test
    fun `init{} - check scores when scores download succeed`() {

        runBlocking {
            Mockito
                .`when`(userManager.isUserLoggedIn())
                .thenReturn(true)

            Mockito
                .`when`(userManager.getLoggedUser())
                .thenReturn(player)

            Mockito
                .`when`(accountManager.getUsersScores(player))
                .thenReturn(Result.Success(scores))

            val newViewModel = HomeViewModel(userManager, accountManager)
            newViewModel.userScores.observeOnce { testScores ->
                assertThat(testScores.size, `is`(scores.size))
            }
        }
    }

    @Test
    fun `scoreChosen() - verify account service method call`() {
        val score = Score()
        viewModel.scoreChosen(score)

        Mockito.verify(accountManager).setScoreChosen(score)
    }
}