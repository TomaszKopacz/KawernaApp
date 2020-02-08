package com.tomaszkopacz.kawernaapp.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val cameraManager: CameraManager
): ViewModel() {

    val state = MutableLiveData<String>()

    init {
        checkIsCameraEnabled()
    }

    private fun checkIsCameraEnabled() {
        if (cameraManager.isCameraEnabled()) {
            state.postValue(Message.CAMERA_ENABLED)

        } else {
            state.postValue(Message.CAMERA_DISABLED)
        }
    }
}