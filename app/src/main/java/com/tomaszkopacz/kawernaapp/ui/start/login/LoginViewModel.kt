package com.tomaszkopacz.kawernaapp.ui.start.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.managers.UserManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    var state = MutableLiveData<String>()

    fun login(mail: String, password: String) {
        GlobalScope.launch {

            when (val result = userManager.login(mail, password)) {
                is Result.Success -> {
                    state.postValue(Message.LOGIN_SUCCEED)
                }

                is Result.Failure -> {
                    state.postValue(result.message.text)
                }
            }
        }
    }
}