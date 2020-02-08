package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.ui.game.GameActivity
import com.tomaszkopacz.kawernaapp.ui.game.players.ScanPlayersFragment
import com.tomaszkopacz.kawernaapp.ui.game.result.ResultFragment
import com.tomaszkopacz.kawernaapp.ui.game.scores.PlayersScoresFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface GameComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): GameComponent
    }

    fun inject(activity: GameActivity)
    fun inject(fragment: ScanPlayersFragment)
    fun inject(fragment: PlayersScoresFragment)
    fun inject(fragment: ResultFragment)
}