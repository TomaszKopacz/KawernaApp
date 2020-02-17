package com.tomaszkopacz.kawernaapp.ui.game.players

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.journeyapps.barcodescanner.BarcodeResult
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.managers.GameManager
import com.tomaszkopacz.kawernaapp.managers.UserManager
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
        Log.d("Kawerna", "Text scanned: $scannedText")
        gameManager.addNewPlayer(scannedText, playerListener)
        Log.d("Kawerna", "New player added")
    }

    fun confirm() {
        gameManager.confirmPlayers()
    }

    private fun exposePlayers(players: ArrayList<Player>) {
        this.players.postValue(players)
    }
}
