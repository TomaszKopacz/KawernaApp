package com.tomaszkopacz.kawernaapp.functionalities.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Score
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Exception


class HomeFragment : Fragment() {

    private lateinit var layout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_home, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()

        downloadScores()
    }

    private fun setListeners() {
        new_game_button.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeToPlayers()
            findNavController().navigate(direction)
        }
    }

    private fun downloadScores() {
        when (AuthManager.getLoggedUser()?.email) {
            "tk@op.pl" -> FireStoreRepository().getScores("Tomasz", scoresListener)
            "arek@op.pl" -> FireStoreRepository().getScores("Arek", scoresListener)
        }
    }

    private val scoresListener = object : FireStoreRepository.DownloadScoresListener {
        override fun onSuccess(scores: ArrayList<Score>) {
            for (score in scores)
                Log.d("Kawerna", score.player + " " + score.place + "/" + score.playersCount)
        }

        override fun onFailure(exception: Exception) {
            Log.d("Kawerna", "Download scores failed")
        }

    }
}
