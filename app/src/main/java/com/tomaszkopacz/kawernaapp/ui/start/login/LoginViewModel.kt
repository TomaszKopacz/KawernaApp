package com.tomaszkopacz.kawernaapp.ui.start.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.managers.UserManager
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    var state = MutableLiveData<String>()

    fun login(mail: String, password: String) {
        userManager.login(mail, password, loginListener)
    }

    private val loginListener = object : UserManager.UserListener {
        override fun onSuccess(player: Player, message: Message) {
            state.postValue(message.text)
        }

        override fun onFailure(message: Message) {
            state.postValue(message.text)
        }
    }
}