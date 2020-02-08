package com.tomaszkopacz.kawernaapp.functionalities.game.players

import androidx.lifecycle.MutableLiveData
import com.journeyapps.barcodescanner.BarcodeResult
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.game.GameManager
import com.tomaszkopacz.kawernaapp.user.UserManager
import javax.inject.Inject

class ScanPlayersViewModel @Inject constructor(
    private val userManager: UserManager,
    private val gameManager: GameManager
) {

    var players: MutableLiveData<ArrayList<Player>> = MutableLiveData()

    var state = MutableLiveData<String>()

    private val playerListener = object : GameManager.PlayerListener {
        override fun onSuccess(player: Player, message: Message) {
            state.postValue(message.text)
            exposePlayers(gameManager.getPlayers())
        }

        override fun onFailure(message: Message) {
            state.postValue(message.text)
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
}
