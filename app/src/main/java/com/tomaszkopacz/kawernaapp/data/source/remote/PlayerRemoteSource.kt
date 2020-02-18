package com.tomaszkopacz.kawernaapp.data.source.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.source.PlayerSource
import com.tomaszkopacz.kawernaapp.util.extensions.MD5
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

    override suspend fun updatePassword(email: String, password: String): Result<Player> {
        val result = database.collection(PLAYERS_COLLECTION)
            .whereEqualTo("email", email).get().await()

        return if (result.documents.size == 0) {
            Result.Failure(Message(Message.PLAYER_NOT_FOUND))

        } else {
            val player = result.documents[0].toObject(Player::class.java)!!
            val documentId = result.documents[0].id

            val newPlayer = Player(player.email, player.name, password.MD5())
            database.collection(PLAYERS_COLLECTION).document(documentId).set(newPlayer).await()

            Result.Success(newPlayer)
        }
    }

    companion object {
        const val PLAYERS_COLLECTION = "Players"
    }
}