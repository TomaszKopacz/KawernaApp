package com.tomaszkopacz.kawernaapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tomaszkopacz.kawernaapp.authentication.AuthManager
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import com.tomaszkopacz.kawernaapp.functionalities.game.players.ScanPlayersViewModel
import com.tomaszkopacz.kawernaapp.functionalities.game.result.ResultScreenViewModel
import com.tomaszkopacz.kawernaapp.functionalities.game.scores.PlayersScoresViewModel
import com.tomaszkopacz.kawernaapp.functionalities.main.board.HomeViewModel
import com.tomaszkopacz.kawernaapp.functionalities.main.profile.AccountViewModel
import com.tomaszkopacz.kawernaapp.functionalities.main.statistics.StatisticsViewModel
import com.tomaszkopacz.kawernaapp.functionalities.start.login.LoginViewModel
import com.tomaszkopacz.kawernaapp.functionalities.start.register.SignUpViewModel
import com.tomaszkopacz.kawernaapp.game.GameManager
import com.tomaszkopacz.kawernaapp.scores.ScoreManager
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.user.UserManager

class ViewModelFactory (
    private val authManager: AuthManager,
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {

            val userManager = UserManager(fireStoreRepository, sharedPrefsRepository)
            val scoresManager = ScoreManager(fireStoreRepository)
            val gameManager = GameManager(fireStoreRepository, sharedPrefsRepository)
            when {

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(userManager)

                isAssignableFrom(SignUpViewModel::class.java) ->
                    SignUpViewModel(userManager)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(userManager, scoresManager)

                isAssignableFrom(ScanPlayersViewModel::class.java) ->
                    ScanPlayersViewModel(userManager, gameManager)

                isAssignableFrom(PlayersScoresViewModel::class.java) ->
                    PlayersScoresViewModel(sharedPrefsRepository, fireStoreRepository)

                isAssignableFrom(StatisticsViewModel::class.java) ->
                    StatisticsViewModel(userManager, scoresManager)

                isAssignableFrom(AccountViewModel::class.java) ->
                    AccountViewModel(userManager)

                isAssignableFrom(ResultScreenViewModel::class.java) ->
                    ResultScreenViewModel(sharedPrefsRepository, fireStoreRepository)

                else -> throw IllegalArgumentException("Unknown ViewModel class ${this.name}")
            }
        } as T
}