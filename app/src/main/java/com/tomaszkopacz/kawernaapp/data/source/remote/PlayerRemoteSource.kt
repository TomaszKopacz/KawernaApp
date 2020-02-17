package com.tomaszkopacz.kawernaapp.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.source.PlayerSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRemoteSource @Inject constructor() : PlayerSource {

    private val database = FirebaseFirestore.getInstance()

    override suspend fun addPlayer(player: Player): Result<Player> {
        database.collection(PLAYERS_COLLECTION).document().set(player).await()
        return Result.Success(player)
    }

    override suspend fun getPlayer(email: String): Result<Player> {
        val result = database.collection(PLAYERS_COLLECTION)
            .whereEqualTo("email", email)
            .get().await()

        return if (result.documents.size == 0) {
            Result.Failure(Message(Message.PLAYER_NOT_FOUND))

        } else {
            Result.Success(result.documents[0].toObject(Player::class.java)!!)
        }
    }

    companion object {
        const val PLAYERS_COLLECTION = "Players"
    }
}