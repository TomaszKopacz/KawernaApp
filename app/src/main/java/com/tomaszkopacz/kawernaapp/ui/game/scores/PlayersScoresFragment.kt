package com.tomaszkopacz.kawernaapp.ui.game.scores

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.ui.game.GameActivity
import kotlinx.android.synthetic.main.fragment_players_scores.*
import javax.inject.Inject

class PlayersScoresFragment : Fragment() {

    private lateinit var layout: View

    @Inject
    lateinit var viewModel: PlayersScoresViewModel

    private var scoresAdapter: ScoresAdapter = ScoresAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as GameActivity).gameComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_players_scores, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

        subscribeToViewModel()
        subscribeToUI()
    }

    private fun initRecyclerView() {
        scoresAdapter.setHasStableIds(true)
        scoresAdapter.setScoreWatcher(scoreWatcher)

        scores_list.layoutManager = LinearLayoutManager(context)
        scores_list.adapter = scoresAdapter
    }

    private val scoreWatcher: ScoreWatcher = object : ScoreWatcher {
        override fun onScoreChanged(position: Int, score: Int) {
            viewModel.updateScore(position, score)
        }
    }

    private fun subscribeToViewModel() {
        setCategoryObserver()
        setScoresObserver()
    }

    private fun setCategoryObserver() {
        viewModel.currentCategory.observe(viewLifecycleOwner, Observer { category ->
            updateCategoryLayout(category)
            scoresAdapter.loadCategory(category)
        })
    }

    private fun updateCategoryLayout(category: ScoreCategory) {
        updateCategoryText(category)
        controlButtons(category)
        focusFirstItem()
    }

    private fun updateCategoryText(category: ScoreCategory) {
        category_text.text = resources.getString(
            resources.getIdentifier(
                category.name,
                "string",
                context!!.packageName
            )
        )
    }

    private fun controlButtons(category: ScoreCategory) {
        when (category) {
            ScoreCategory.ANIMALS -> {
                setPreviousButtonVisibility(false)
                setNextButtonVisibility(true)
                setSubmitButtonVisibility(false)
            }

            ScoreCategory.GOLD -> {
                setPreviousButtonVisibility(true)
                setNextButtonVisibility(false)
                setSubmitButtonVisibility(true)
            }

            else -> {
                setPreviousButtonVisibility(true)
                setNextButtonVisibility(true)
                setSubmitButtonVisibility(false)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setPreviousButtonVisibility(isVisible: Boolean) {
        previous_category_button.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    @SuppressLint("RestrictedApi")
    private fun setNextButtonVisibility(isVisible: Boolean) {
        next_category_button.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    @SuppressLint("RestrictedApi")
    private fun setSubmitButtonVisibility(isVisible: Boolean) {
        submit_button.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun focusFirstItem() {
        scores_list.requestFocus()
    }

    private fun setScoresObserver() {
        viewModel.playersScores.observe(viewLifecycleOwner, Observer {
            scoresAdapter.loadScores(it)
        })
    }

    private fun subscribeToUI() {
        setPreviousCategoryButtonListener()
        setNextCategoryButtonListener()
        setSubmitButtonListener()
    }

    private fun setPreviousCategoryButtonListener() {
        previous_category_button.setOnClickListener {
            viewModel.previousCategory()
        }
    }

    private fun setNextCategoryButtonListener() {
        next_category_button.setOnClickListener {
            viewModel.nextCategory()
        }
    }

    private fun setSubmitButtonListener() {
        submit_button.setOnClickListener {
            viewModel.submitScores()
            navigateToResultScreen()
        }
    }

    private fun navigateToResultScreen() {
        val direction = PlayersScoresFragmentDirections.actionPlayersScoresToResultScreen()
        findNavController().navigate(direction)
    }
}
