package com.tomaszkopacz.kawernaapp.data

data class Score(var player: String, var game: String, var date: String, var playersCount: Int) {

    constructor() : this("", "", "", 0)

    var livestock: Int = 0
    var livestockLack: Int = 0
    var cereal: Int = 0
    var vegetables: Int = 0
    var rubies: Int = 0
    var dwarfs: Int = 0
    var areas: Int = 0
    var unusedAreas: Int = 0
    var premiumAreas: Int = 0
    var gold: Int = 0

    var place: Int = 0

    fun total() : Int {
        return (livestock) +
                (livestockLack * (-2)) +
                (cereal/2 + cereal%2) +
                (vegetables) +
                (rubies) +
                (dwarfs) +
                (areas) +
                (unusedAreas * (-1)) +
                (premiumAreas) +
                (gold)
    }
}