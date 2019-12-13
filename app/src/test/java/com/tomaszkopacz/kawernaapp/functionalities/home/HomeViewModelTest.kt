package com.tomaszkopacz.kawernaapp.functionalities.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.base.CharMatcher.any
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

class HomeViewModelTest {

    @Rule @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var authManager: AuthManager
    private lateinit var fireStoreRepository: FireStoreRepository

    @Before
    fun setUp() {
        fireStoreRepository = Mockito.mock(FireStoreRepository::class.java)
        authManager = Mockito.mock(AuthManager::class.java)
        viewModel = HomeViewModel(authManager, fireStoreRepository)
    }

    @Test
    fun testConstructor_WhenInitialized_ThenStateIs_NONE() {
        assertTrue(viewModel.state.value == HomeViewModel.STATE_NONE)
    }

    @Test
    fun testDownloadScores_WhenLoggedPlayerIsNull_ThenStateIs_DOWNLOAD_FAILED () {
        Mockito.`when`(authManager.getLoggedUser()).thenReturn(null)

        viewModel.downloadScores()

        assertTrue(viewModel.state.value == HomeViewModel.STATE_SCORES_DOWNLOAD_FAILED)
    }

    @Test
    fun testDownloadScores_WhenLoggedPlayerIsEmpty_ThenStateIs_DOWNLOAD_FAILED () {
        Mockito.`when`(authManager.getLoggedUser()).thenReturn("")

        viewModel.downloadScores()

        assertTrue(viewModel.state.value == HomeViewModel.STATE_SCORES_DOWNLOAD_FAILED)
    }

    @Test
    fun testDownloadScores_WhenLoggedPlayerEmailHasNoAtSign_ThenStateIs_DOWNLOAD_FAILED () {
        Mockito.`when`(authManager.getLoggedUser()).thenReturn("johnsmithgmail.com")

        viewModel.downloadScores()

        assertTrue(viewModel.state.value == HomeViewModel.STATE_SCORES_DOWNLOAD_FAILED)
    }

    @Test
    fun testDownloadScores_WhenPlayerNotFoundInDatabase_ThenScoresListIsEmpty () {
        val listenerCaptor = ArgumentCaptor.forClass(FireStoreRepository.DownloadScoresListener::class.java)

        Mockito.`when`(authManager.getLoggedUser()).thenReturn("playernotfound@gmail.com")

        // when no player found in database repo returns empty list in onSuccess callback
        viewModel.downloadScores()
        Mockito.verify(fireStoreRepository).getPlayerScores(Mockito.anyString(), listenerCaptor.capture())
        listenerCaptor.value.onSuccess(ArrayList())

        assertTrue(viewModel.state.value == HomeViewModel.STATE_SCORES_DOWNLOADED)
        assertTrue(viewModel.userScores.value != null)
        assertTrue(viewModel.userScores.value!!.isEmpty())
    }
//
//    @Test
//    fun testDownloadScores_WhenPlayerExistsInDatabase_ThenStateIs_DOWNLOADED () {
//
//    }
//
//    @Test
//    fun testDownloadScores_WhenPlayerExistsInDatabase_ThenPlayersListIsSet () {
//
//    }
//
//    @Test
//    fun getUserScores() {
//    }
//
//    @Test
//    fun setUserScores() {
//    }
//
//    @Test
//    fun getState() {
//    }
//
//    @Test
//    fun setState() {
//    }
//
//    @Test
//    fun downloadScores() {
//    }
}