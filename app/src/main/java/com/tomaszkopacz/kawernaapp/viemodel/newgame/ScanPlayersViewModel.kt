package com.tomaszkopacz.kawernaapp.viemodel.newgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.journeyapps.barcodescanner.BarcodeResult
import com.tomaszkopacz.kawernaapp.data.Player

class ScanPlayersViewModel : ViewModel() {

    companion object {
        private const val TAG = "Kawerna"
    }

    private var players: ArrayList<Player> = ArrayList()
    var playersData: MutableLiveData<ArrayList<Player>> = MutableLiveData()

    fun scanPerformed(result: BarcodeResult) {
        val scannedPlayer = Player(result.text)

        if (!players.contains(scannedPlayer)) {
            players.add(Player(result.text))
            playersData.value = players
        }
    }

    fun playersConfirmed() {

    }
}
