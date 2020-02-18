package com.tomaszkopacz.kawernaapp.managers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.repository.PlayersRepository
import com.tomaszkopacz.kawernaapp.util.extensions.MD5
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class UserManagerTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userManager: UserManager

    private lateinit var playersRepository: PlayersRepository
    private lateinit var networkManager: NetworkManager

    @Before
    fun setUp() {
        playersRepository = Mockito.mock(PlayersRepository::class.java)
        networkManager = Mockito.mock(NetworkManager::class.java)

        userManager = UserManager(playersRepository, networkManager)
    }

    @Test
    fun `isUserLoggedIn() - check returned value when user is logged in`() {
        Mockito
            .`when`(playersRepository.isUserLoggedIn())
            .thenReturn(true)

        assertThat(userManager.isUserLoggedIn(), `is`(true))
    }

    @Test
    fun `isUserLoggedIn() - check returned value when user is not logged in`() {
        Mockito
            .`when`(playersRepository.isUserLoggedIn())
            .thenReturn(false)

        assertThat(userManager.isUserLoggedIn(), `is`(false))
    }

    @Test
    fun `getLoggedUser() - check returned value for test user logged in`() {
        Mockito
            .`when`(playersRepository.getLoggedUser())
            .thenReturn(Player("email", "name", "password"))

        assertTrue(userManager.getLoggedUser() != null)
        assertThat(userManager.getLoggedUser()!!.email, `is`("email"))
        assertThat(userManager.getLoggedUser()!!.name, `is`("name"))
        assertThat(userManager.getLoggedUser()!!.password, `is`("password"))
    }

    @Test
    fun `getLoggedUser() - check returned value when no user user is logged in`() {
        Mockito
            .`when`(playersRepository.getLoggedUser())
            .thenReturn(null)

        assertTrue(userManager.getLoggedUser() == null)
    }

    @Test
    fun `login() - check result for empty data provided`() {
        runBlocking {
            val emptyEmail = ""
            val emptyPassword = ""
            val email = "email"
            val password = "password"

            assertTrue(userManager.login(emptyEmail, emptyPassword) is Result.Failure)
            assertTrue(userManager.login(emptyEmail, password) is Result.Failure)
            assertTrue(userManager.login(email, emptyPassword) is Result.Failure)
        }
    }

    @Test
    fun `login() - check result when no internet connection`() {
        runBlocking {

            Mockito
                .`when`(networkManager.isNetworkConnected())
                .thenReturn(false)

            val email = "email"
            val password = "password"

            assertTrue(userManager.login(email, password) is Result.Failure)
            assertTrue((userManager
                .login(email, password) as Result.Failure)
                .message.text == Message.NO_INTERNET_CONNECTION)
        }
    }

    @Test
    fun `login() - check result when user is not present in database`() {
        runBlocking {

            Mockito
                .`when`(networkManager.isNetworkConnected())
                .thenReturn(true)

            Mockito
                .`when`(playersRepository.findUserByEmail(Mockito.anyString()))
                .thenReturn(Result.Failure(Message(Message.LOGIN_EMAIL_NOT_FOUND)))

            val email = "email"
            val password = "password"

            assertTrue(userManager
                .login(email, password) is Result.Failure)

            assertTrue((userManager
                .login(email, password) as Result.Failure)
                .message.text == Message.LOGIN_EMAIL_NOT_FOUND)
        }
    }

    @Test
    fun `login() - check result when password is incorrect`() {
        runBlocking {

            Mockito
                .`when`(networkManager.isNetworkConnected())
                .thenReturn(true)

            Mockito
                .`when`(playersRepository.findUserByEmail(Mockito.anyString()))
                .thenReturn(Result.Success(Player("email", "name", "password".MD5())))

            val email = "email"
            val incorrectPassword = "incorrectPassword"

            assertTrue(userManager
                .login(email, incorrectPassword) is Result.Failure)

            assertTrue((userManager
                .login(email, incorrectPassword) as Result.Failure)
                .message.text == Message.PASSWORD_INCORRECT)
        }
    }

    @Test
    fun `login() - check is login successful`() {
        runBlocking {

            Mockito
                .`when`(networkManager.isNetworkConnected())
                .thenReturn(true)

            Mockito
                .`when`(playersRepository.findUserByEmail(Mockito.anyString()))
                .thenReturn(Result.Success(Player("email", "name", "password".MD5())))

            val email = "email"
            val password = "password"

            assertTrue(userManager
                .login(email, password) is Result.Success)

            assertTrue((userManager
                .login(email, password) as Result.Success)
                .data.email == "email")
        }
    }

    @Test
    fun `register() - check result for empty data provided`() {
        runBlocking {
            val emptyEmail = ""
            val emptyName = ""
            val emptyPassword = ""
            val email = "email"
            val name = "name"
            val password = "password"

            assertTrue(userManager.register(emptyEmail, emptyName, emptyPassword) is Result.Failure)

            assertTrue(userManager.register(emptyEmail, emptyName, password) is Result.Failure)
            assertTrue(userManager.register(emptyEmail, name, emptyPassword) is Result.Failure)
            assertTrue(userManager.register(email, emptyName, emptyPassword) is Result.Failure)

            assertTrue(userManager.register(emptyEmail, name, password) is Result.Failure)
            assertTrue(userManager.register(email, name, emptyPassword) is Result.Failure)
        }
    }

    @Test
    fun `register() - check result when no internet connection`() {
        runBlocking {

            Mockito
                .`when`(networkManager.isNetworkConnected())
                .thenReturn(false)

            val email = "email"
            val name = "name"
            val password = "password"

            assertTrue(userManager.register(email, name, password) is Result.Failure)
            assertTrue((userManager
                .register(email, name, password) as Result.Failure)
                .message.text == Message.NO_INTERNET_CONNECTION)
        }
    }

    @Test
    fun `register() - check result email is occupied`() {
        runBlocking {

            val email = "email"
            val name = "name"
            val password = "password"

            Mockito
                .`when`(networkManager.isNetworkConnected())
                .thenReturn(true)

            Mockito
                .`when`(playersRepository.findUserByEmail(email))
                .thenReturn(Result.Success(Player(email, name, password)))

            assertTrue(userManager.register(email, name, password) is Result.Failure)
            assertTrue((userManager
                .register(email, name, password) as Result.Failure)
                .message.text == Message.EMAIL_OCCUPIED)
        }
    }

    @Test
    fun `register() - check is registration successful`() {
        runBlocking {

            val email = "email"
            val name = "name"
            val password = "password"

            Mockito
                .`when`(networkManager.isNetworkConnected())
                .thenReturn(true)

            Mockito
                .`when`(playersRepository.findUserByEmail(email))
                .thenReturn(Result.Failure(Message(Message.PLAYER_NOT_FOUND)))

            Mockito
                .`when`(playersRepository.registerUser(anyObject()))
                .thenReturn(Result.Success((Player(email, name, password))))

            assertTrue(userManager.register(email, name, password) is Result.Success)
        }
    }

    private fun <T> anyObject(): T {
        return Mockito.any<T>()
    }
}