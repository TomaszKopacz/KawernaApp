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
        const val LOGIN_FAILED = "Nie można zalogować"

        // register
        const val EMAIL_OCCUPIED = "Podany adres email jest już zajęty"
        const val REGISTRATION_SUCCEED = "Zarejestrowano"

        // restore password
        const val PASSWORD_RESTORED = "Wysłano email z hasłem"

        // scores
        const val SCORES_DOWNLOAD_IN_PROGRESS = "Trwa pobieranie wyników"
        const val SCORES_DOWNLOADED = "Pobrano wyniki"
        const val NO_SCORES = "Brak wyników"

        // scan players
        const val PLAYER_FOUND = "Znaleziono gracza"
        const val PLAYER_NOT_FOUND = "Nie znaleziono gracza"

        // game
        const val GAME_SUBMITTED = "Zakończono grę"
        const val GAME_SUBMITION_FAILED = "Nie można zapisać wyników"

        // camera
        const val CAMERA_ENABLED = "Aparat jest włączony"
        const val CAMERA_DISABLED = "Aparat jest wyłączony"
    }
}