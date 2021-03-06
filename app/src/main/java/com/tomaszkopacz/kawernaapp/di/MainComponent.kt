package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.ui.main.MainActivity
import com.tomaszkopacz.kawernaapp.ui.main.board.HomeFragment
import com.tomaszkopacz.kawernaapp.ui.main.details.DetailsFragment
import com.tomaszkopacz.kawernaapp.ui.main.profile.AccountFragment
import com.tomaszkopacz.kawernaapp.ui.main.statistics.StatisticsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: StatisticsFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: DetailsFragment)
}