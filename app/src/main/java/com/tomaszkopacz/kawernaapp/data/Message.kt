package com.tomaszkopacz.kawernaapp.data

data class Message(val text: String) {

    companion object {
        // internet
        const val NO_INTERNET_CONNECTION = "Brak połączenia z internetem"

        // user credentials
        const val EMPTY_DATA = "Pola są puste"

        // login
        const val LOGIN_EMAIL_NOT_FOUND = "Nie znaleziono użytkownika"
        const val PASSWORD_INCORRECT = "Hasło jest nieprawidłowe"
        const val LOGIN_SUCCEED = "Zalogowano"

        // register
        const val EMAIL_OCCUPIED = "Podany adres email jest już zajęty"
        const val REGISTRATION_SUCCEED = "Zarejestrowano"

        // scores
        const val SCORES_DOWNLOAD_IN_PROGRESS = "Trwa pobieranie wyników"
        const val SCORES_DOWNLOADED = "Pobrano wyniki"
        const val SCORES_DOWNLOAD_FAILED = "Nie można pobrać wyników"

        // scan players
        const val PLAYER_FOUND = "Znaleziono gracza"
        const val PLAYER_NOT_FOUND = "Nie znaleziono gracza"

        // camera
        const val CAMERA_ENABLED = "Aparat jest wyłączony"
        const val CAMERA_DISABLED = "Aparat jest wyłączony"
    }
}