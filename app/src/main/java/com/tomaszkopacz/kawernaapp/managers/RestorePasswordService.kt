package com.tomaszkopacz.kawernaapp.managers

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.repository.PlayersRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class RestorePasswordService @Inject constructor(
    private val emailService: EmailService,
    private val playersRepository: PlayersRepository
) {

    suspend fun restorePassword(email: String): Result<Player> {
        val newPassword = randomString()

        val result = playersRepository.updatePassword(email, newPassword)

        if (result is Result.Success) {
            sendEmailWithPassword(email, newPassword)
        }

        return result
    }

    private fun randomString(): String {
        val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

        val sb = StringBuilder(NEW_PASSWORD_LENGTH)
        for (i in 0 until NEW_PASSWORD_LENGTH-1) {
            sb.append(chars[Random.nextInt(chars.length)])
        }

        return sb.toString()
    }

    private fun sendEmailWithPassword(email: String, password: String) {
        val subject = "Przywracanie hasla w aplikacji Kawerna"
        val body = "To jest Twoje nowe haslo: ${password}. Nie udostepniaj go nikomu oraz zmien je jak najszybciej."

        emailService.sendEmail(email, subject, body)
    }

    companion object {
        const val NEW_PASSWORD_LENGTH = 8
    }
}