package com.tomaszkopacz.kawernaapp.functionalities.scanplayers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.journeyapps.barcodescanner.BarcodeResult
import com.tomaszkopacz.kawernaapp.data.Player

class ScanPlayersViewModel : ViewModel() {

    private var mPlayers: ArrayList<Player> = ArrayList()
    var players: MutableLiveData<ArrayList<Player>> = MutableLiveData()

    fun scanPerformed(result: BarcodeResult) {
        val scannedPlayer = Player(result.text)

        if (!mPlayers.contains(scannedPlayer)) {
            mPlayers.add(Player(result.text))
            players.value = mPlayers
        }
    }
}
