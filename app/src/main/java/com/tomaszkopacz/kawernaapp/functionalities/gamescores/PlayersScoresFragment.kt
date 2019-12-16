package com.tomaszkopacz.kawernaapp.functionalities.gamescores

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsGameManager
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_players_scores.*

class PlayersScoresFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: PlayersScoresViewModel

    private var scoresAdapter: ScoresAdapter = ScoresAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_players_scores, container, false)


        viewModel = ViewModelProviders
            .of(this, ViewModelFactory(AuthManager(), FireStoreRepository()))
            .get(PlayersScoresViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initGame()
        initRecyclerView()

        setObservers()
        setListeners()
    }

    private fun initGame() {
        val gameId = getGameIdFromSharedPrefs()
        val players = getPlayersFromSharedPrefs()
        viewModel.initGame(gameId ?: "", players ?: ArrayList())
    }

    private fun getGameIdFromSharedPrefs() = SharedPrefsGameManager.getInstance(context!!).getGameId()

    private fun getPlayersFromSharedPrefs() = SharedPrefsGameManager.getInstance(context!!).getPlayers()

    private fun initRecyclerView() {
        scoresAdapter.setHasStableIds(true)
        scoresAdapter.setScoreWatcher(scoreWatcher)

        scores_list.layoutManager = LinearLayoutManager(context)
        scores_list.adapter = scoresAdapter
    }

    private val scoreWatcher: ScoreWatcher = object :
        ScoreWatcher {
        override fun onScoreChanged(position: Int, score: Int) {
            viewModel.updateCurrentScore(position, score)
        }
    }

    private fun setObservers() {
        setCategoryObserver()
        setScoresObserver()
        setStateObserver()
    }

    private fun setCategoryObserver() {
        viewModel.currentCategory.observe(this, Observer {category ->
            setCategoryLayout(category)
            scoresAdapter.loadCategory(category)
        })
    }

    private fun setScoresObserver() {
        viewModel.scores.observe(this, Observer {
            scoresAdapter.loadScores(it)
        })
    }

    private fun setStateObserver() {
        viewModel.state.observe(this, Observer {
            when(it) {
                PlayersScoresViewModel.SCORE_UPLOADED -> navigateToResultScreen()
                PlayersScoresViewModel.FAILED_TO_UPLOAD_SCORE -> failureMessage()
            }
        })
    }

    private fun navigateToResultScreen() {
        val direction = PlayersScoresFragmentDirections.actionPlayersScoresToResultScreen()
        findNavController().navigate(direction)
    }

    private fun failureMessage() {
        Toast.makeText(context, "Error occured! Cannot submit the scores.", Toast.LENGTH_LONG)
            .show()
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

    private fun setCategoryLayout(category: ScoreCategory) {
        updateCategoryText(category)
        controlButtons(category)
        focusFirstItem()
    }

    private fun updateCategoryText(category: ScoreCategory) {
        category_text.text = resources.getString(resources.getIdentifier(category.name, "string", context!!.packageName))
    }

    private fun controlButtons(category: ScoreCategory) {
        when (category) {
            ScoreCategory.LIVESTOCK -> {
                setPreviousButtonVisibility(false)
                setNextButtonVisibility(true)
                setSubmitButtonVisibility(false)
            }

            ScoreCategory.BEG -> {
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
}
