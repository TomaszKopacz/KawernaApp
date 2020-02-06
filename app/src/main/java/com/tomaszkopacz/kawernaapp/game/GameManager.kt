package com.tomaszkopacz.kawernaapp.game

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.database.DataBaseRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GameManager(
    private val repository: DataBaseRepository
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
        repository.getPlayerByEmail(email, dbPlayerListener)
    }

    private val dbPlayerListener = object : DataBaseRepository.PlayerListener {
        override fun onSuccess(player: Player) {
            if (!players.contains(player)) {
                players.add(player)
                playersScores.add(PlayerScore(player, Score(player.email, gameId!!, date!!)))
            }

            playerListener?.onSuccess(player)
        }

        override fun onFailure(exception: Exception) {
            playerListener?.onFailure(exception)
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
            ScoreCategory.TOTAL -> {
            }
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
        saveScoresToFireStore()
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
        fun onSuccess(player: Player)
        fun onFailure(exception: Exception)
    }
}