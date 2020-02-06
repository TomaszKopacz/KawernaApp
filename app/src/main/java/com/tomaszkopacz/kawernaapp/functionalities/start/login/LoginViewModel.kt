package com.tomaszkopacz.kawernaapp.functionalities.start.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.user.UserManager
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class LoginViewModel(
    private val userManager: UserManager
) : ViewModel() {

    var state = MutableLiveData<String>()

    fun login(mail: String, password: String) {
        userManager.login(mail, password, loginListener)
    }

    private val loginListener = object : UserManager.UserListener {
        override fun onSuccess(player: Player) {
            loginSucceed()
        }

        override fun onFailure(exception: Exception) {
            loginFailed()
        }
    }

    private fun loginSucceed() {
        state.postValue(STATE_LOGIN_SUCCEED)
    }

    private fun loginFailed() {
        state.postValue(STATE_LOGIN_FAILED)
    }

    companion object {
        const val STATE_LOGIN_SUCCEED = "LOGIN SUCCEED"
        const val STATE_LOGIN_FAILED = "LOGIN FAILED"
    }
}