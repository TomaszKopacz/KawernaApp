package com.tomaszkopacz.kawernaapp.ui.password

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.managers.RestorePasswordService
import com.tomaszkopacz.kawernaapp.util.extensions.observeOnce
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class RestorePasswordViewModelTest {

    private lateinit var service: RestorePasswordService
    private lateinit var viewModel: RestorePasswordViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        service = Mockito.mock(RestorePasswordService::class.java)
        viewModel = RestorePasswordViewModel(service)
    }

    @Test
    fun `restorePassword() - check state when email of player provided`() {
        val email = ""

        runBlocking {
            viewModel.restorePassword(email)
        }

        viewModel.getState().observeOnce { state ->
            assertThat(state, `is`(Message.EMPTY_DATA))
        }
    }

    @Test
    fun `restorePassword() - check state when error occurs in service`() {
        val email = "email"

        runBlocking {
            Mockito
                .`when`(service.updatePassword(email))
                .thenReturn(Result.Failure(Message("FAILURE")))

            viewModel.restorePassword(email)
        }

        viewModel.getState().observeOnce { state ->
            assertThat(state, `is`("FAILURE"))
        }
    }

    @Test
    fun `restorePassword() - check state when password restored correctly`() {
        val email = "email"

        runBlocking {
            Mockito
                .`when`(service.updatePassword(email))
                .thenReturn(Result.Success(Player()))

            viewModel.restorePassword(email)
        }

        viewModel.getState().observeOnce { state ->
            assertThat(state, `is`(Message.PASSWORD_UPDATED))
        }
    }
}