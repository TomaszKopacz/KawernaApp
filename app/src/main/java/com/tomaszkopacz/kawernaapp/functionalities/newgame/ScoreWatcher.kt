package com.tomaszkopacz.kawernaapp.functionalities.newgame

interface ScoreWatcher {
    fun onScoreChanged(position: Int, score: Int)
}