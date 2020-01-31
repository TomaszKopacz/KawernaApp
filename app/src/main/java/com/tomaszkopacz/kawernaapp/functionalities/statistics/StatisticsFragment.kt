package com.tomaszkopacz.kawernaapp.functionalities.statistics


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: StatisticsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_statistics, container, false)

        viewModel = ViewModelProviders
            .of(this, ViewModelFactory(
                AuthManager(),
                SharedPrefsRepository.getInstance(context!!),
                FireStoreRepository()))
            .get(StatisticsViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCategoryPicker()

        subscribeToViewModel()
    }

    private fun initCategoryPicker() {
        spinnerCategory.adapter = CategoryAdapter(context!!)
    }

    private fun subscribeToViewModel() {
        observeState()
        observeMaxValue()
        observeMeanValue()
    }

    private fun observeState() {
        viewModel.state.observe(this, Observer { state->
            when (state) {
                StatisticsViewModel.STATE_SCORES_DOWNLOAD_IN_PROGRESS -> { }
                StatisticsViewModel.STATE_SCORES_DOWNLOAD_SUCCEED -> { subscribeToUI() }
                StatisticsViewModel.STATE_SCORES_DOWNLOAD_FAILED -> { }
            }
        })
    }

    private fun observeMaxValue() {
        viewModel.maxScore.observe(this, Observer { score ->
            max_score.text = score.toString()
        })
    }

    private fun observeMeanValue() {
        viewModel.meanScore.observe(this, Observer { score ->
            mean_score.text = score.toString()
        })
    }

    private fun subscribeToUI() {
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                viewModel.categoryChanged(parent!!.getItemAtPosition(position) as ScoreCategory)
            }
        }
    }
}