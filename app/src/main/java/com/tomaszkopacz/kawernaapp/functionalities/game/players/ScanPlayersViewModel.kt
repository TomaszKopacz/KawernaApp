package com.tomaszkopacz.kawernaapp.functionalities.game.players

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.journeyapps.barcodescanner.BarcodeResult
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.game.GameManager
import com.tomaszkopacz.kawernaapp.user.UserManager

class ScanPlayersViewModel(
    private val userManager: UserManager,
    private val gameManager: GameManager
) {

    var players: MutableLiveData<ArrayList<Player>> = MutableLiveData()

    var state = MutableLiveData<String>()

    private val playerListener = object : GameManager.PlayerListener {
        override fun onSuccess(player: Player) {
            playerFound()
            exposePlayers(gameManager.getPlayers())
        }

        override fun onFailure(exception: Exception) {
            playerNotFound()
        }
    }

    init {
        initPlayersList()
    }

    private fun initPlayersList() {
        gameManager.addNewPlayer(userManager.getLoggedUser()!!.email, playerListener)
    }

    fun scanPerformed(barcodeResult: BarcodeResult) {
        val scannedText = barcodeResult.text
        gameManager.addNewPlayer(scannedText, playerListener)
    }

    fun confirm() {
        gameManager.confirmPlayers()
    }

    private fun exposePlayers(players: ArrayList<Player>) {
        this.players.postValue(players)
    }

    private fun playerFound() {
        state.postValue(PLAYER_FOUND)
    }

    private fun playerNotFound() {
        state.postValue(PLAYER_NOT_FOUND)
    }

    companion object {
        const val PLAYER_FOUND = "PLAYER FOUND"
        const val PLAYER_NOT_FOUND = "PLAYER NOT FOUND"
    }
}
