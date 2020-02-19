package com.tomaszkopacz.kawernaapp.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.managers.UserManager
import com.tomaszkopacz.kawernaapp.util.extensions.observeOnce
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SplashViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userManager: UserManager

    @Before
    fun setUp() {
        userManager = Mockito.mock(UserManager::class.java)
    }

    @Test
    fun `init{} - check state when no user is logged in`() {
        Mockito.`when`(userManager.isUserLoggedIn()).thenReturn(false)

        val newViewModel = SplashViewModel(userManager)

        newViewModel.state.observeOnce { state ->
            assertThat(state, `is`(Message.STATE_NO_USER_LOGGED_IN))
        }
    }

    @Test
    fun `init{} - check state when some user is logged in`() {
        Mockito.`when`(userManager.isUserLoggedIn()).thenReturn(true)

        val newViewModel = SplashViewModel(userManager)

        newViewModel.state.observeOnce { state ->
            assertThat(state, `is`(Message.STATE_USER_LOGGED_IN))
        }
    }
}