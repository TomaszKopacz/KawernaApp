package com.tomaszkopacz.kawernaapp.ui.start.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.managers.UserManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    var state = MutableLiveData<String>()

    fun register(mail: String, name: String, password: String) {
        GlobalScope.launch {
            when (val result = userManager.register(mail, name, password)) {
                is Result.Success -> {
                    state.postValue(Message.REGISTRATION_SUCCEED)
                }

                is Result.Failure -> {
                    state.postValue(result.message.text)
                }
            }
        }
    }
}