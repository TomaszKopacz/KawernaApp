package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.functionalities.game.GameActivity
import com.tomaszkopacz.kawernaapp.functionalities.game.players.ScanPlayersFragment
import com.tomaszkopacz.kawernaapp.functionalities.game.result.ResultFragment
import com.tomaszkopacz.kawernaapp.functionalities.game.scores.PlayersScoresFragment
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