package com.tomaszkopacz.kawernaapp.ui.main.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var viewModel: DetailsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.score.observe(this, Observer { score ->
            writeScoreToLayout(score)
        })
    }

    private fun writeScoreToLayout(score: Score) {
        tv_total.text = score.total().toString()
        tv_place.text = score.place.toString()
        tv_players_num.text = score.playersCount.toString()
        tv_date.text = score.date
        tv_animals.text = score.livestock.toString()
        tv_animals_lack.text = score.livestockLack.toString()
        tv_cereal.text = score.cereal.toString()
        tv_vegetables.text = score.vegetables.toString()
        tv_rubies.text = score.rubies.toString()
        tv_dwarfs.text = score.dwarfs.toString()
        tv_areas.text = score.areas.toString()
        tv_unused_areas.text = score.unusedAreas.toString()
        tv_premium_areas.text = score.premiumAreas.toString()
        tv_gold.text = score.gold.toString()
    }

}
