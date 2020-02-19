package com.tomaszkopacz.kawernaapp.ui.main.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.managers.AccountManager
import com.tomaszkopacz.kawernaapp.util.extensions.observeOnce
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DetailsViewModelTest {

    private lateinit var accountManager: AccountManager

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        accountManager = Mockito.mock(AccountManager::class.java)
    }

    @Test
    fun `init{} - check correct score is exposed on init`() {
        val scoreChosen = Score("player", "game", "date")

        Mockito
            .`when`(accountManager.getScoreChosen())
            .thenReturn(scoreChosen)

        val viewModel = DetailsViewModel(accountManager)
        viewModel.score.observeOnce { score ->
            assertThat(score.player, `is`(scoreChosen.player))
            assertThat(score.game, `is`(scoreChosen.game))
            assertThat(score.date, `is`(scoreChosen.date))
        }
    }
}