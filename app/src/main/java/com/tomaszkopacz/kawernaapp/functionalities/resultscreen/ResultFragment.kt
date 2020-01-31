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
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {

    private lateinit var viewModel: ResultScreenViewModel
    private lateinit var layout: View

    private val adapter = ScoresAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layout = inflater.inflate(R.layout.fragment_result, container, false)
        viewModel = ViewModelProviders
            .of(
                this, ViewModelFactory(
                    AuthManager(),
                    SharedPrefsRepository.getInstance(context!!),
                    FireStoreRepository()
                )
            )
            .get(ResultScreenViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

        subscribeToUI()
        subscribeToViewModel()
    }

    private fun initRecyclerView() {
        result_scores_recycler_view.layoutManager = LinearLayoutManager(context)
        result_scores_recycler_view.adapter = adapter
    }

    private fun subscribeToUI() {
        accept_button.setOnClickListener {
            viewModel.submit()
            (activity as GameActivity).goToMainActivity()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.resultScores.observe(this, Observer { resultScores ->
            adapter.loadScores(resultScores)
        })
    }
}
