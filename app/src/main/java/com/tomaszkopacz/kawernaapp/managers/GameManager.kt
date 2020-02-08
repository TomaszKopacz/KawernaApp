package com.tomaszkopacz.kawernaapp.managers

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.database.DataBaseRepository
import com.tomaszkopacz.kawernaapp.di.ActivityScope
import com.tomaszkopacz.kawernaapp.data.Message
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@ActivityScope
class GameManager @Inject constructor(
    private val repository: DataBaseRepository,
    private val networkManager: NetworkManager
) {

    private var gameId: String? = null
    private var date: String? = null
    private var players = ArrayList<Player>()
    private var playersScores = ArrayList<PlayerScore>()

    private var playerListener: PlayerListener? = null

    init {
        gameId = generateUniqueGameId()
        date = getCurrentDateString()
    }

    private fun generateUniqueGameId(): String {
        val millis = System.currentTimeMillis().toString()
        val random = (0..1000000).random().toString()
        return millis + random
    }

    private fun getCurrentDateString(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun addNewPlayer(email: String, listener: PlayerListener?) {
        this.playerListener = listener
        getPlayerByEmail(email)
    }

    private fun getPlayerByEmail(email: String) {
        if (networkManager.isNetworkConnected()) {
            repository.getPlayerByEmail(email, dbPlayerListener)

        } else {
            playerListener?.onFailure(Message(Message.NO_INTERNET_CONNECTION))
        }
    }

    private val dbPlayerListener = object : DataBaseRepository.PlayerListener {
        override fun onSuccess(player: Player) {
            if (!players.contains(player)) {
                players.add(player)
                playersScores.add(PlayerScore(player, Score(player.email, gameId!!, date!!)))
            }

            playerListener?.onSuccess(player,
                Message(Message.PLAYER_FOUND)
            )
        }

        override fun onFailure(exception: Exception) {
            playerListener?.onFailure(Message(Message.PLAYER_NOT_FOUND))
        }
    }

    fun confirmPlayers() {
        fixPlayersNumber()
    }

    private fun fixPlayersNumber() {
        val playersCount = players.size
        for (playerScore in playersScores) {
            playerScore.score.playersCount = playersCount
        }

    }

    fun updateScoreForCategory(position: Int, score: Int, category: ScoreCategory) {
        when (category) {
            ScoreCategory.ANIMALS -> playersScores[position].score.livestock = score
            ScoreCategory.ANIMALS_LACK -> playersScores[position].score.livestockLack = score
            ScoreCategory.CEREAL -> playersScores[position].score.cereal = score
            ScoreCategory.VEGETABLES -> playersScores[position].score.vegetables = score
            ScoreCategory.RUBIES -> playersScores[position].score.rubies = score
            ScoreCategory.DWARFS -> playersScores[position].score.dwarfs = score
            ScoreCategory.AREAS -> playersScores[position].score.areas = score
            ScoreCategory.UNUSED_AREAS -> playersScores[position].score.unusedAreas = score
            ScoreCategory.PREMIUM_AREAS -> playersScores[position].score.premiumAreas = score
            ScoreCategory.GOLD -> playersScores[position].score.gold = score
        }
    }

    fun sortScores() {
        playersScores.sortByDescending { it.score.total() }
    }

    fun submitGame() {
        fixPlayersPlaces()
    }

    private fun fixPlayersPlaces() {
        sortScores()
        playersScores.forEachIndexed { index, playerScore ->
            playerScore.score.place = index + 1
        }
    }

    fun submitResult() {
        if(networkManager.isNetworkConnected()) {
            saveScoresToFireStore()

        } else {
            playerListener?.onFailure(Message(Message.NO_INTERNET_CONNECTION))
        }
    }

    private fun saveScoresToFireStore() {
        for (playerScore in playersScores)
            repository.addScore(playerScore.score, scoresListener)
    }

    private var scoresListener = object : DataBaseRepository.ScoresListener {
        override fun onSuccess(scores: ArrayList<Score>) {

        }

        override fun onFailure(exception: Exception) {

        }
    }

    fun getPlayers(): ArrayList<Player> {
        return players
    }

    fun getPlayersScores(): ArrayList<PlayerScore> {
        return playersScores
    }

    interface PlayerListener {
        fun onSuccess(player: Player, message: Message)
        fun onFailure(message: Message)
    }
}