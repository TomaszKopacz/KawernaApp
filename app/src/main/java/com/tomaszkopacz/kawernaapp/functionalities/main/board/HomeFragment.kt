package com.tomaszkopacz.kawernaapp.functionalities.main.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity
import com.tomaszkopacz.kawernaapp.authentication.AuthManager
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: HomeViewModel

    private val scoresAdapter = ScoresAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        layout = inflater.inflate(R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders
            .of(this, ViewModelFactory(
                AuthManager(),
                SharedPrefsRepository.getInstance(context!!),
                FireStoreRepository()
            ))
            .get(HomeViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

        subscribeToUI()
        subscribeToViewModel()
    }

    private fun initRecyclerView() {
        home_scores_recycler_view.layoutManager = GridLayoutManager(context, 2)
        home_scores_recycler_view.adapter = scoresAdapter
    }

    private fun subscribeToUI() {
        new_game_button.setOnClickListener {
            (activity as MainActivity).navigateToNewGameActivity()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.userScores.observe(this, Observer {scores ->
            scoresAdapter.loadScores(scores)
        })
    }
}
