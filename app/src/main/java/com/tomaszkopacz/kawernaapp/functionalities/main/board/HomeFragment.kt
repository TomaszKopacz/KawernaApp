package com.tomaszkopacz.kawernaapp.functionalities.main.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import com.tomaszkopacz.kawernaapp.dialogs.ProgressDialog
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.extensions.MD5
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : Fragment() {

    private lateinit var layout: View

    @Inject
    lateinit var viewModel: HomeViewModel

    private val scoresAdapter = ScoresAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        layout = inflater.inflate(R.layout.fragment_home, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showProgressBar()
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
        observeState()
        observeScores()
    }

    private fun observeState() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                Message.SCORES_DOWNLOADED -> { }
                else -> {
                    hideProgressBar()
                    showErrorMessage(state)
                }
            }
        })
    }

    private fun observeScores() {
        viewModel.userScores.observe(this, Observer { scores ->
            scoresAdapter.loadScores(scores)
            hideProgressBar()
        })
    }

    private fun showProgressBar() {
        ProgressDialog.show(context!!)
    }

    private fun hideProgressBar() {
        ProgressDialog.hide()
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
