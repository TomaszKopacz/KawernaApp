package com.tomaszkopacz.kawernaapp.ui.game.result

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.ui.game.GameActivity
import kotlinx.android.synthetic.main.fragment_result.*
import javax.inject.Inject

class ResultFragment : Fragment() {

    @Inject
    lateinit var viewModel: ResultScreenViewModel

    private lateinit var layout: View

    private val adapter = ScoresAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as GameActivity).gameComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layout = inflater.inflate(R.layout.fragment_result, container, false)

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
        }
    }

    private fun subscribeToViewModel() {
        viewModel.state.observe(this, Observer {state ->
            when (state) {
                Message.GAME_SUBMITTED -> {
                    (activity as GameActivity).goToMainActivity()
                }

                Message.GAME_SUBMITION_FAILED -> {
                    showErrorMessage(state)
                }
            }
        })

        viewModel.resultScores.observe(this, Observer { resultScores ->
            adapter.loadScores(resultScores)
        })
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
