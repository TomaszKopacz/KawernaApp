package com.tomaszkopacz.kawernaapp.data

import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class StatisticsResult {

    var maxTotal: Int? = null
    var worstTotal: Int? = null
    var meanTotal: Int? = null
    var bestScoresCount: Int? = null
    var winsCount: Int? = null
    var gamesCount: Int? = null
    var seriesTotal: LineGraphSeries<DataPoint>? = null

    var maxForCategory: Int? = null
    var meanForCategory: Int? = null
    var seriesForCategory: LineGraphSeries<DataPoint>? = null
}