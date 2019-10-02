package com.tomaszkopacz.kawernaapp.ui.newgame

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
import com.tomaszkopacz.kawernaapp.data.PlayersParcelable
import com.tomaszkopacz.kawernaapp.viemodel.newgame.ScanPlayersViewModel
import kotlinx.android.synthetic.main.fragment_scan_players.*

class ScanPlayersFragment : Fragment() {

    companion object {
        private const val TAG = "Kawerna"
    }

    private lateinit var layout: View
    private lateinit var viewModel: ScanPlayersViewModel

    private var playersAdapter: PlayersAdapter = PlayersAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

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
        viewModel.playersData.observe(this, Observer {
            if (it != null) {
                playersAdapter.loadPlayers(it)
            }
        })
    }

    private fun setListeners() {
        confirm_button.setOnClickListener {
            confirmPlayers()
        }
    }

    private fun confirmPlayers() {
        val players = viewModel.playersData.value

        if(players != null && players.isNotEmpty())
            goToScores(PlayersParcelable().apply { this.players = players })

        else
            noPlayersMessage()
    }

    private fun goToScores(players: PlayersParcelable) {
        val direction = ScanPlayersFragmentDirections.actionScanToScores(players)
        findNavController().navigate(direction)
    }

    private fun noPlayersMessage() {
        Toast.makeText(context, "No useres scanned", Toast.LENGTH_LONG).show()
    }
}
