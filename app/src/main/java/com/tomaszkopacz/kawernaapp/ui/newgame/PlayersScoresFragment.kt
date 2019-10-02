package com.tomaszkopacz.kawernaapp.ui.newgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Categories
import com.tomaszkopacz.kawernaapp.data.PlayersParcelable
import com.tomaszkopacz.kawernaapp.viemodel.newgame.PlayersScoresViewModel
import kotlinx.android.synthetic.main.fragment_players_scores.*

class PlayersScoresFragment : Fragment() {

    companion object {
        private const val TAG = "Kawerna"
    }

    private lateinit var layout: View
    private lateinit var viewModel: PlayersScoresViewModel

    private var scoresAdapter: ScoresAdapter = ScoresAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_players_scores, container, false)

        viewModel = ViewModelProviders.of(this).get(PlayersScoresViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getPlayers()
        initRecyclerView()

        setObservers()
        setListeners()
    }

    private fun getPlayers() {
        val players = arguments?.getParcelable<PlayersParcelable>("listOfPlayers")?.players
        viewModel.setPlayers(players ?: ArrayList())
    }

    private fun initRecyclerView() {
        scoresAdapter.setHasStableIds(true)
        scoresAdapter.setScoreWatcher(scoreWatcher)

        scores_list.layoutManager = LinearLayoutManager(context)
        scores_list.adapter = scoresAdapter
    }

    private val scoreWatcher: ScoreWatcher = object : ScoreWatcher {
        override fun onScoreChanged(position: Int, score: Int) {
            viewModel.updateCurrentScore(position, score)
        }
    }

    private fun setObservers() {
        setCategoryObserver()
        setScoresObserver()
    }

    private fun setCategoryObserver() {
        viewModel.currentCategory.observe(this, Observer {
            setCategoryLayout(it)
            scoresAdapter.loadCategory(it)
        })
    }

    private fun setScoresObserver() {
        viewModel.scoresData.observe(this, Observer {
            scoresAdapter.loadScores(it)
        })
    }

    private fun setListeners() {
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
        }
    }

    private fun setCategoryLayout(category: Int) {
        updateCategoryText(category)
        controlButtons(category)
        focusFirstItem()
    }

    private fun updateCategoryText(category: Int) {
        when (category) {
            Categories.LIVESTOCK -> category_text.text = "Livestock and dogs"
            Categories.LIVESTOCK_LACK -> category_text.text = "Missing livestock"
            Categories.CEREAL -> category_text.text = "Cereal"
            Categories.VEGETABLES -> category_text.text = "Vegetables"
            Categories.RUBIES -> category_text.text = "Rubies"
            Categories.DWARFS -> category_text.text = "Dwarfs"
            Categories.AREAS -> category_text.text = "Improvements, pastures, mines"
            Categories.UNUSED_AREAS -> category_text.text = "Unused areas"
            Categories.PREMIUM_AREAS -> category_text.text = "Rooms, warehouses, chambers"
            Categories.GOLD -> category_text.text = "Gold coins"
            Categories.BEGS -> category_text.text = "Begging tokens"
        }
    }

    private fun controlButtons(category: Int) {
        when (category) {
            Categories.LIVESTOCK -> {
                setPreviousButtonVisibility(false)
                setNextButtonVisibility(true)
                setSubmitButtonVisibility(false)
            }

            Categories.BEGS -> {
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

    private fun setPreviousButtonVisibility(isVisible: Boolean) {
        previous_category_button.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun setNextButtonVisibility(isVisible: Boolean) {
        next_category_button.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun setSubmitButtonVisibility(isVisible: Boolean) {
        submit_button.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun focusFirstItem() {
        scores_list.requestFocus()
    }
}
