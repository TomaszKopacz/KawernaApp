package com.tomaszkopacz.kawernaapp.ui.main

import com.tomaszkopacz.kawernaapp.managers.UserManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var userManager: UserManager

    @Before
    fun setUp() {
        userManager = Mockito.mock(UserManager::class.java)
        viewModel = MainViewModel(userManager)
    }

    @Test
    fun `logout() - check user service method call on logout`() {
        viewModel.logout()

        Mockito
            .verify(userManager)
            .logout()
    }
}