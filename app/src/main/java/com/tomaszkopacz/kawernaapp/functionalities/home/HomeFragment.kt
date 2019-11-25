package com.tomaszkopacz.kawernaapp.functionalities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tomaszkopacz.kawernaapp.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var layout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_home, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {
        new_game_button.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeToPlayers()
            findNavController().navigate(direction)
        }
    }
}
