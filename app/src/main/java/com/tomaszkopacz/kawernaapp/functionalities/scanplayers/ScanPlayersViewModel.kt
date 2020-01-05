package com.tomaszkopacz.kawernaapp.functionalities.scanplayers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.journeyapps.barcodescanner.BarcodeResult
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.extensions.isEmailPattern
import java.lang.Exception

class ScanPlayersViewModel(
    private val authManager: AuthManager,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private var mPlayers: ArrayList<Player> = ArrayList()
    var players: MutableLiveData<ArrayList<Player>> = MutableLiveData()

    var state = MutableLiveData<String>()

    fun init() {
        val loggedUserMail = authManager.getLoggedUser()
        fireStoreRepository.getPlayerByEmail(loggedUserMail!!, downloadPlayerListener)
    }

    fun scanPerformed(barcodeResult: BarcodeResult) {
        val scannedText = barcodeResult.text
        if (scannedText.isEmailPattern()) {
            fireStoreRepository.getPlayerByEmail(scannedText, downloadPlayerListener)
        }
    }

    private val downloadPlayerListener = object : FireStoreRepository.DownloadPlayerListener {
        override fun onSuccess(player: Player?) {
            if (player != null) {
                addNewPlayerToList(player)
                state.postValue(PLAYER_FOUND)

            } else {
                state.postValue(PLAYER_NOT_FOUND)
            }
        }

        override fun onFailure(exception: Exception) { state.postValue(FAILURE)}
    }

    private fun addNewPlayerToList(player: Player) {
        if (!mPlayers.contains(player)) {
            mPlayers.add(player)
            players.value = mPlayers
        }
    }

    companion object {
        const val PLAYER_FOUND = "PLAYER FOUND"
        const val PLAYER_NOT_FOUND = "PLAYER NOT FOUND"
        const val FAILURE = "FAILURE"
    }
}
