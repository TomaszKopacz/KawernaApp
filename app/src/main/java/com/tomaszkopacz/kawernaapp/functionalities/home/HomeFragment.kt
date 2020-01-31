package com.tomaszkopacz.kawernaapp.functionalities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.activities.MainActivity
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.annotations.TestOnly


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
                FireStoreRepository()))
            .get(HomeViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        setObservers()
        setListeners()

        viewModel.downloadScores()
    }

    private fun initRecyclerView() {
        home_scores_recycler_view.layoutManager = GridLayoutManager(context, 2)
        home_scores_recycler_view.adapter = scoresAdapter
    }

    private fun setObservers() {
        viewModel.userScores.observe(this, Observer {scores ->
            scoresAdapter.loadScores(scores)
        })
    }

    private fun setListeners() {
        new_game_button.setOnClickListener {
            (activity as MainActivity).navigateToNewGameActivity()
        }
    }

    @TestOnly
    fun setTestViewModel(testViewModel: HomeViewModel) {
        this.viewModel = testViewModel
    }
}
