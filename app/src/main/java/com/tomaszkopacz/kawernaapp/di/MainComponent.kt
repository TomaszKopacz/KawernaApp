package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.functionalities.main.board.HomeFragment
import com.tomaszkopacz.kawernaapp.functionalities.main.profile.AccountFragment
import com.tomaszkopacz.kawernaapp.functionalities.main.statistics.StatisticsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(fragment: HomeFragment)
    fun inject(fragment: StatisticsFragment)
    fun inject(fragment: AccountFragment)
}