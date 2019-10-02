package com.tomaszkopacz.kawernaapp.data

data class Score(var player: Player) {

    var livestock: Int? = null
    var livestockLack: Int? = null
    var cereal: Int? = null
    var vegetables: Int? = null
    var rubies: Int? = null
    var dwarfs: Int? = null
    var areas: Int? = null
    var unusedAreas: Int? = null
    var premiumAreas: Int? = null
    var gold: Int? = null
    var begs: Int? = null

    fun total() : Int {
        return (livestock ?: 0) +
                (livestockLack ?: 0) +
                (cereal ?: 0) +
                (vegetables ?: 0) +
                (rubies ?: 0) +
                (dwarfs ?: 0) +
                (areas ?: 0) +
                (unusedAreas ?: 0) +
                (premiumAreas ?: 0) +
                (gold ?: 0) +
                (begs ?: 0)
    }
}