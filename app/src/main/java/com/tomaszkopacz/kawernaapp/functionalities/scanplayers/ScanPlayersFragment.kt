package com.tomaszkopacz.kawernaapp.functionalities.scanplayers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsGameManager
import kotlinx.android.synthetic.main.fragment_scan_players.*

class ScanPlayersFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: ScanPlayersViewModel

    private var playersAdapter: PlayersAdapter =
        PlayersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        layout = inflater.inflate(R.layout.fragment_scan_players, container, false)
        viewModel = ViewModelProviders.of(this).get(ScanPlayersViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        setScanner()
        setObservers()
        setListeners()
    }

    override fun onResume() {
        super.onResume()

        resumeScanner()
    }

    override fun onPause() {
        super.onPause()

        pauseScanner()
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = playersAdapter
    }

    private fun setScanner() {
        qr_code_scanner.setStatusText("")
        qr_code_scanner.decodeContinuous(barcodeCallback)
    }

    private val barcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            viewModel.scanPerformed(result!!)
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {

        }

    }

    private fun resumeScanner() {
        if (!qr_code_scanner.isActivated)
            qr_code_scanner.resume()
    }

    private fun pauseScanner() {
        qr_code_scanner.pause()
    }

    private fun setObservers() {
        setPlayersObserver()
    }

    private fun setPlayersObserver() {
        viewModel.players.observe(this, Observer { players ->
            playersAdapter.loadPlayers(players)
        })
    }

    private fun setListeners() {
        confirm_button.setOnClickListener {
            confirmPlayers()
        }
    }

    private fun confirmPlayers() {
        val players = viewModel.players.value

        if (players != null && players.isNotEmpty()) {
            setGameSharedPrefs()
            navigateToScoresScreen()

        } else
            noPlayersMessage()
    }

    private fun setGameSharedPrefs() {
        addGameIdToSharedPrefs()
        addPlayersToSharedPrefs()
    }

    private fun addGameIdToSharedPrefs() {
        val id = generateUniqueGameId()
        SharedPrefsGameManager.getInstance(context!!).saveGameId(id)
    }

    private fun generateUniqueGameId(): String {
        val millis = System.currentTimeMillis().toString()
        val random = (0..1000000).random().toString()
        return millis + random
    }

    private fun addPlayersToSharedPrefs() {
        val players = viewModel.players.value
        SharedPrefsGameManager.getInstance(context!!).savePlayers(players!!)
    }

    private fun navigateToScoresScreen() {
        val direction = ScanPlayersFragmentDirections.actionScanToPlayersScores()
        findNavController().navigate(direction)
    }

    private fun noPlayersMessage() {
        Toast.makeText(context, "No useres scanned", Toast.LENGTH_LONG).show()
    }
}
