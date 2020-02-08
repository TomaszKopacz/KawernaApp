package com.tomaszkopacz.kawernaapp.ui.game.scores

interface ScoreWatcher {
    fun onScoreChanged(position: Int, score: Int)
}