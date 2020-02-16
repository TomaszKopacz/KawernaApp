package com.tomaszkopacz.kawernaapp.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.source.PlayerSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRemoteSource @Inject constructor() : PlayerSource {

    private val database = FirebaseFirestore.getInstance()

    override fun addPlayer(player: Player, listener: PlayerSource.PlayerListener?) {
        database.collection(PLAYERS_COLLECTION).document()
            .set(player)
            .addOnSuccessListener { listener?.onSuccess(player) }
            .addOnFailureListener { exception -> listener?.onFailure(exception) }
    }

    override fun getPlayer(email: String, listener: PlayerSource.PlayerListener?) {
        database.collection(PLAYERS_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() == 0) {
                    listener?.onFailure(Exception("User not found"))

                } else {
                    val player = documents.documents[0].toObject(Player::class.java)
                    listener?.onSuccess(player!!)
                }
            }
            .addOnFailureListener { exception ->
                listener?.onFailure(exception)
            }
    }

    companion object {
        const val PLAYERS_COLLECTION = "Players"
    }
}