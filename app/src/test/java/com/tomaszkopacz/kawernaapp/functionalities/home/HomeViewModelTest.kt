package com.tomaszkopacz.kawernaapp.functionalities.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Score
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
    fun `testConstructor - when view model created, then state is NONE`() {
        assertTrue(viewModel.state.value == HomeViewModel.STATE_NONE)
    }

    @Test
    fun `downloadScores() - when logged player is null then state is DOWNLOAD_FAILED` () {
        Mockito.`when`(authManager.getLoggedUser()).thenReturn(null)

        viewModel.downloadScores()

        assertTrue(viewModel.state.value == HomeViewModel.STATE_SCORES_DOWNLOAD_FAILED)
    }

    @Test
    fun `downloadScores() - when logged player is empty then state is DOWNLOAD_FAILED`() {
        Mockito.`when`(authManager.getLoggedUser()).thenReturn("")

        viewModel.downloadScores()

        assertTrue(viewModel.state.value == HomeViewModel.STATE_SCORES_DOWNLOAD_FAILED)
    }

    @Test
    fun `downloadScores() - when player email hasn't at sign then state is DOWNLOAD_FAILED` () {
        Mockito.`when`(authManager.getLoggedUser()).thenReturn("johnsmithgmail.com")

        viewModel.downloadScores()

        assertTrue(viewModel.state.value == HomeViewModel.STATE_SCORES_DOWNLOAD_FAILED)
    }

    @Test
    fun `downloadScores() - when player doesn't exist in db then state is DOWNLOADED` () {
        val listenerCaptor = ArgumentCaptor.forClass(FireStoreRepository.DownloadScoresListener::class.java)

        Mockito.`when`(authManager.getLoggedUser()).thenReturn("playernotfound@gmail.com")

        // when no player found in database repo returns empty list in onSuccess callback
        viewModel.downloadScores()
        Mockito.verify(fireStoreRepository).getPlayerScores(Mockito.anyString(), listenerCaptor.capture())
        listenerCaptor.value.onSuccess(ArrayList())

        assertTrue(viewModel.state.value == HomeViewModel.STATE_SCORES_DOWNLOADED)
    }

    @Test
    fun `downloadScores() - when player exists in db then state is DOWNLOADED` () {
        val listenerCaptor = ArgumentCaptor.forClass(FireStoreRepository.DownloadScoresListener::class.java)
        val scores = ArrayList<Score>()
        for (i in 1..3) scores.add(Score())

        Mockito.`when`(authManager.getLoggedUser()).thenReturn("someplayer@gmail.com")

        viewModel.downloadScores()
        Mockito.verify(fireStoreRepository).getPlayerScores(Mockito.anyString(), listenerCaptor.capture())
        listenerCaptor.value.onSuccess(scores)

        assertTrue(viewModel.state.value == HomeViewModel.STATE_SCORES_DOWNLOADED)

    }

    @Test
    fun `downloadScores() - when player doesn't exist in db then scores list is empty` () {
        val listenerCaptor = ArgumentCaptor.forClass(FireStoreRepository.DownloadScoresListener::class.java)

        Mockito.`when`(authManager.getLoggedUser()).thenReturn("playernotfound@gmail.com")

        // when no player found in database repo returns empty list in onSuccess callback
        viewModel.downloadScores()
        Mockito.verify(fireStoreRepository).getPlayerScores(Mockito.anyString(), listenerCaptor.capture())
        listenerCaptor.value.onSuccess(ArrayList())

        assertTrue(viewModel.userScores.value != null)
        assertTrue(viewModel.userScores.value!!.isEmpty())
    }

    @Test
    fun `downloadScores() - when player exists in db then scores list is set` () {
        val listenerCaptor = ArgumentCaptor.forClass(FireStoreRepository.DownloadScoresListener::class.java)
        val scores = ArrayList<Score>()
        for (i in 1..3)
            scores.add(Score())

        Mockito.`when`(authManager.getLoggedUser()).thenReturn("someplayer@gmail.com")

        viewModel.downloadScores()
        Mockito.verify(fireStoreRepository).getPlayerScores(Mockito.anyString(), listenerCaptor.capture())
        listenerCaptor.value.onSuccess(scores)

        assertTrue(viewModel.userScores.value != null)
        assertTrue(viewModel.userScores.value!!.size == 3)
    }
}