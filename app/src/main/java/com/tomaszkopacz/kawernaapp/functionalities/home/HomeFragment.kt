package com.tomaszkopacz.kawernaapp.functionalities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomaszkopacz.kawernaapp.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: HomeViewModel

    private val scoresAdapter = ScoresAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        layout = inflater.inflate(R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        setObservers()
        setListeners()

        viewModel.downloadScores()
    }

    private fun initRecyclerView() {
        home_scores_recycler_view.layoutManager = LinearLayoutManager(context)
        home_scores_recycler_view.adapter = scoresAdapter
    }

    private fun setObservers() {
        viewModel.userScores.observe(this, Observer {scores ->
            scoresAdapter.loadScores(scores)
        })
    }

    private fun setListeners() {
        new_game_button.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeToPlayers()
            findNavController().navigate(direction)
        }
    }
}
