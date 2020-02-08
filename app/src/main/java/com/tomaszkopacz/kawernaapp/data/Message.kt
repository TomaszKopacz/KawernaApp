package com.tomaszkopacz.kawernaapp.data

data class Message(val text: String) {

    companion object {
        // internet
        const val NO_INTERNET_CONNECTION = "No internet connection"

        // user credentials
        const val EMPTY_DATA = "Data are empty"

        // login
        const val LOGIN_EMAIL_NOT_FOUND = "Player not found"
        const val PASSWORD_INCORRECT = "Password incorrect"
        const val LOGIN_SUCCEED = "Login succeed"

        // register
        const val EMAIL_OCCUPIED = "Email occupied"
        const val REGISTRATION_SUCCEED = "Registration succeed"

        // scores
        const val SCORES_DOWNLOAD_IN_PROGRESS = "Scores download in progress"
        const val SCORES_DOWNLOADED = "Scores downloaded successfully"
        const val SCORES_DOWNLOAD_FAILED = "Cannot download scores"

        // scan players
        const val PLAYER_FOUND = "Player found"
        const val PLAYER_NOT_FOUND = "Player not found"

        // camera
        const val CAMERA_ENABLED = "Camera enabled"
        const val CAMERA_DISABLED = "Camera disabled"
    }
}