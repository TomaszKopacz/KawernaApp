package com.tomaszkopacz.kawernaapp.ui.start.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.managers.UserManager
import com.tomaszkopacz.kawernaapp.util.extensions.observeOnce
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userManager: UserManager
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        userManager = Mockito.mock(UserManager::class.java)
        viewModel = LoginViewModel(userManager)
    }

    @Test
    fun `login() - check state when login succeed`() {
        runBlocking {
            Mockito
                .`when`(userManager.login(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Result.Success(Player()))

            viewModel.login("mail", "password")
        }

        viewModel.state.observeOnce { state ->
            assertThat(state, `is`(Message.LOGIN_SUCCEED))
        }

    }

    @Test
    fun `login() - check state when login failed`() {
        runBlocking {
            Mockito
                .`when`(userManager.login(
                    Mockito.anyString(),
                    Mockito.anyString()))
                .thenReturn(Result.Failure(Message("Failure")))

            viewModel.login("mail", "password")
        }

        viewModel.state.observeOnce { state ->
            assertThat(state, `is`("Failure"))
        }

    }
}