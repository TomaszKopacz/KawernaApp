package com.tomaszkopacz.kawernaapp.functionalities.resultscreen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.activities.GameActivity
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsGameManager
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {

    private lateinit var viewModel: ResultScreenViewModel
    private lateinit var layout: View

    private val adapter = ScoresAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        layout = inflater.inflate(R.layout.fragment_result, container, false)
        viewModel = ViewModelProviders.of(this).get(ResultScreenViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initResultScreen()

        setListeners()
        setObservers()
    }

    private fun initRecyclerView() {
        result_scores_recycler_view.layoutManager = LinearLayoutManager(context)
        result_scores_recycler_view.adapter = adapter
    }

    private fun initResultScreen() {
        val gameId = SharedPrefsGameManager.getInstance(context!!).getGameId()
        val players = SharedPrefsGameManager.getInstance((context!!)).getPlayers()
        if (gameId != null) viewModel.showGameResults(gameId, players!!)
    }

    private fun setListeners() {
        accept_button.setOnClickListener {
            clearSharedPrefs()
            (activity as GameActivity).goToMainActivity()
        }
    }

    private fun clearSharedPrefs() {
        SharedPrefsGameManager.getInstance(context!!).clearAll()
    }

    private fun setObservers() {
        viewModel.resultScores.observe(this, Observer { resultScores ->
            adapter.loadScores(resultScores)
        })
    }
}
