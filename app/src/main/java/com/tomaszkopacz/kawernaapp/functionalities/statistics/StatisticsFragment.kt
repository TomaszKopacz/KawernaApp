package com.tomaszkopacz.kawernaapp.functionalities.statistics


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: StatisticsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_statistics, container, false)

        viewModel = ViewModelProviders
            .of(this, ViewModelFactory(AuthManager(), FireStoreRepository()))
            .get(StatisticsViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCategoryPicker()
        initViewModel()

        subscribeToUI()
        subscribeToViewModel()
    }

    private fun initCategoryPicker() {
        category_picker.minValue = 0
        category_picker.maxValue = StatisticsViewModel.CATEGORY_NUM-1

        val categories = arrayOfNulls<String>(StatisticsViewModel.CATEGORY_NUM)

        categories[StatisticsViewModel.TOTAL] = resources.getString(R.string.TOTAL)
        categories[StatisticsViewModel.ANIMALS] = resources.getString(R.string.LIVESTOCK)
        categories[StatisticsViewModel.CEREALS] = resources.getString(R.string.CEREAL)
        categories[StatisticsViewModel.VEGETABLES] = resources.getString(R.string.VEGETABLES)
        categories[StatisticsViewModel.AREAS] = resources.getString(R.string.AREAS)
        categories[StatisticsViewModel.PREMIUM_AREAS] = resources.getString(R.string.PREMIUM_AREAS)
        categories[StatisticsViewModel.GOLD] = resources.getString(R.string.GOLD)

        category_picker.displayedValues = categories
    }

    private fun initViewModel() {
        viewModel.init()
    }

    private fun subscribeToUI() {
        category_picker.setOnValueChangedListener { _, _, newVal ->
            viewModel.categoryChanged(newVal)
        }
    }

    private fun subscribeToViewModel() {
        observeMaxValue()
        observeMeanValue()
    }

    private fun observeMaxValue() {
        viewModel.maxScore.observe(this, Observer { score ->
            max_score.text = score.toString()
        })
    }

    private fun observeMeanValue() {
        viewModel.meanScore.observe(this, Observer { score ->
            mean_score.text = score.toString()
        })    }
}
