package com.tomaszkopacz.kawernaapp.viemodel.newgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.journeyapps.barcodescanner.BarcodeResult
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsManager

class ScanPlayersViewModel : ViewModel() {

    private var _players: ArrayList<Player> = ArrayList()
    var players: MutableLiveData<ArrayList<Player>> = MutableLiveData()

    fun scanPerformed(result: BarcodeResult) {
        val scannedPlayer = Player(result.text)

        if (!_players.contains(scannedPlayer)) {
            _players.add(Player(result.text))
            players.value = _players
        }
    }
}
