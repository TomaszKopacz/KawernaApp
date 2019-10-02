package com.tomaszkopacz.kawernaapp.ui.newgame

interface ScoreWatcher {
    fun onScoreChanged(position: Int, score: Int)
}