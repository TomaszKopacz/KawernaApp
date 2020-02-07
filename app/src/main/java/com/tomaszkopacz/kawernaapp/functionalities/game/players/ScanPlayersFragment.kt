package com.tomaszkopacz.kawernaapp.functionalities.game.players

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.functionalities.game.GameActivity
import com.tomaszkopacz.kawernaapp.qr.QRScanner
import kotlinx.android.synthetic.main.fragment_scan_players.*
import javax.inject.Inject

class ScanPlayersFragment : Fragment() {

    private lateinit var layout: View

    @Inject
    lateinit var viewModel: ScanPlayersViewModel

    private lateinit var scanner: QRScanner

    private var playersAdapter: PlayersAdapter = PlayersAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as GameActivity).gameComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        layout = inflater.inflate(R.layout.fragment_scan_players, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initScanner()

        subscribeToViewModel()
        subscribeToUI()
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = playersAdapter
    }

    private fun initScanner() {
        scanner = QRScanner(qr_code_scanner, object : BarcodeCallback {

            override fun barcodeResult(result: BarcodeResult?) {
                viewModel.scanPerformed(result!!)
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {

            }
        })
    }

    private fun subscribeToViewModel() {
        setStateObserver()
        setPlayersObserver()
    }

    private fun setStateObserver() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                ScanPlayersViewModel.PLAYER_NOT_FOUND -> showMessage("Cannot find such player")
            }
        })
    }

    private fun setPlayersObserver() {
        viewModel.players.observe(this, Observer { players ->
            playersAdapter.loadPlayers(players)
        })
    }

    private fun subscribeToUI() {
        confirm_button.setOnClickListener {
            viewModel.confirm()
            navigateToScoresScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        scanner.resume()
    }

    override fun onPause() {
        super.onPause()
        scanner.pause()
    }

    private fun navigateToScoresScreen() {
        val direction = ScanPlayersFragmentDirections.actionScanToPlayersScores()
        findNavController().navigate(direction)
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
