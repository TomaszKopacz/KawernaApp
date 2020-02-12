package com.tomaszkopacz.kawernaapp.ui.main.statistics


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.ui.dialogs.ProgressDialog
import com.tomaszkopacz.kawernaapp.ui.main.MainActivity
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
        initGraphs()
        initCategoryPicker()

        subscribeToViewModel()
    }

    private fun initGraphs() {
        graph_total.viewport.isScrollable = true
        graph_total.viewport.isXAxisBoundsManual = true
        graph_total.viewport.isYAxisBoundsManual = true
        graph_total.viewport.setMinX(0.0)
        graph_total.viewport.setMaxX(10.0)
        graph_total.viewport.setMinY(0.0)
        graph_total.viewport.setMaxY(120.0)

        graph_category.viewport.isScrollable = true
        graph_category.viewport.isXAxisBoundsManual = true
        graph_category.viewport.isYAxisBoundsManual = true
        graph_category.viewport.setMinX(0.0)
        graph_category.viewport.setMaxX(10.0)
        graph_category.viewport.setMinY(0.0)
        graph_category.viewport.setMaxY(40.0)
    }

    private fun initCategoryPicker() {
        spinnerCategory.adapter = CategoryAdapter(context!!)
    }

    private fun subscribeToViewModel() {
        observeState()
        observeResult()
    }

    private fun observeState() {
        viewModel.state.observe(this, Observer { state->
            when (state) {
                Message.SCORES_DOWNLOAD_IN_PROGRESS -> {
                    showProgressBar()
                }
                Message.SCORES_DOWNLOADED -> {
                    subscribeToUI()
                    hideProgressBar()
                }
                else -> {
                    hideProgressBar()
                    showErrorMessage(state)
                }
            }
        })
    }

    private fun observeResult() {
        viewModel.result.observe(this, Observer { result ->
            tv_max_total.text = result.maxTotal.toString()
            tv_worst_total.text = result.worstTotal.toString()
            tv_mean_total.text = result.meanTotal.toString()
            tv_hundreds.text = result.bestScoresCount.toString()
            tv_wins.text = result.winsCount.toString()
            tv_games.text = result.gamesCount.toString()

            graph_total.removeAllSeries()
            graph_total.addSeries(result.seriesTotal)
            graph_total.viewport.scrollToEnd()

            tv_max_category.text = result.maxForCategory.toString()
            tv_mean_category.text = result.meanForCategory.toString()

            graph_category.removeAllSeries()
            graph_category.addSeries(result.seriesForCategory)
            graph_category.viewport.scrollToEnd()
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