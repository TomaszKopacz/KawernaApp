package com.tomaszkopacz.kawernaapp.functionalities.main.statistics


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tomaszkopacz.kawernaapp.MyApplication
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity
import kotlinx.android.synthetic.main.fragment_statistics.*
import javax.inject.Inject

class StatisticsFragment : Fragment() {

    private lateinit var layout: View

    @Inject
    lateinit var viewModel: StatisticsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_statistics, container, false)

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