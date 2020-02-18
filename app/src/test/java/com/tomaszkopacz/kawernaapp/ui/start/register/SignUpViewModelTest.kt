package com.tomaszkopacz.kawernaapp.ui.start.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.managers.UserManager
import com.tomaszkopacz.kawernaapp.util.extensions.observeOnce
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SignUpViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userManager: UserManager
    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setUp() {
        userManager = Mockito.mock(UserManager::class.java)
        viewModel = SignUpViewModel(userManager)
    }

    @Test
    fun `register() - check state when register succeed`() {
        runBlocking {
            Mockito
                .`when`(userManager.register(
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString()))
                .thenReturn(Result.Success(Player()))

            viewModel.register("email", "name", "password")
        }

        viewModel.state.observeOnce { state ->
            assertThat(state, `is`(Message.REGISTRATION_SUCCEED))
        }
    }

    @Test
    fun `register() - check state when register failed`() {
        runBlocking {
            Mockito
                .`when`(userManager.register(
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString()))
                .thenReturn(Result.Failure(Message("Failure")))

            viewModel.register("email", "name", "password")
        }

        viewModel.state.observeOnce { state ->
            assertThat(state, `is`("Failure"))
        }
    }

}