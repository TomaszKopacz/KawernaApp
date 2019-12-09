package com.tomaszkopacz.kawernaapp.functionalities.gamescores

interface ScoreWatcher {
    fun onScoreChanged(position: Int, score: Int)
}