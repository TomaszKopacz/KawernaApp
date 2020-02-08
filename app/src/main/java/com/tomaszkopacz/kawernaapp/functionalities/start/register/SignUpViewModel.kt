package com.tomaszkopacz.kawernaapp.functionalities.start.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.user.UserManager
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    var state = MutableLiveData<String>()

    fun register(mail: String, name: String, password: String) {
        userManager.register(mail, name, password, registerListener)
    }

    private val registerListener = object : UserManager.UserListener {
        override fun onSuccess(player: Player, message: Message) {
            state.postValue(message.text)
        }

        override fun onFailure(message: Message) {
            state.postValue(message.text)
        }
    }
}