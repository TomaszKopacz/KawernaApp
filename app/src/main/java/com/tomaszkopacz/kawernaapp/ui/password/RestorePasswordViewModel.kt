package com.tomaszkopacz.kawernaapp.ui.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.managers.RestorePasswordService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class RestorePasswordViewModel @Inject constructor(
    private val restorePasswordService: RestorePasswordService
) {

    private val state = MutableLiveData<String>()

    fun getState(): LiveData<String> = state

    fun restorePassword(email: String) {
        if (email.isEmpty()) {
            state.postValue(Message.EMPTY_DATA)

        } else {
            GlobalScope.launch {
                when (val result = restorePasswordService.restorePassword(email)) {
                    is Result.Success -> {
                        state.postValue(Message.PASSWORD_RESTORED)
                    }

                    is Result.Failure -> {
                        state.postValue(result.message.text)
                    }
                }
            }
        }
    }

}