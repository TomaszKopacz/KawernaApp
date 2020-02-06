package com.tomaszkopacz.kawernaapp.functionalities.game.scores

interface ScoreWatcher {
    fun onScoreChanged(position: Int, score: Int)
}