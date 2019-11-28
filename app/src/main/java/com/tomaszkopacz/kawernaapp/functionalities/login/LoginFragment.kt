package com.tomaszkopacz.kawernaapp.functionalities.login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.functionalities.newgame.PlayersScoresFragmentDirections
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var layout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_login, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {
        TESTBTN.setOnClickListener {
            openMainActivity()
        }
    }

    private fun openMainActivity() {
        val direction = LoginFragmentDirections.actionLoginToMain()
        activity?.finish()
        findNavController().navigate(direction)
    }


}
