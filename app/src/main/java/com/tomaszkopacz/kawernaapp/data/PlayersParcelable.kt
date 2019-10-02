package com.tomaszkopacz.kawernaapp.data

import android.os.Parcel
import android.os.Parcelable

class PlayersParcelable() : Parcelable {

    var players: ArrayList<Player> = ArrayList()

    constructor(parcel: Parcel) : this() {
        this.players = arrayListOf<Player>().apply {
            parcel.readArray(Player::class.java.classLoader)
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeList(players)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayersParcelable> {
        override fun createFromParcel(parcel: Parcel): PlayersParcelable {
            return PlayersParcelable(parcel)
        }

        override fun newArray(size: Int): Array<PlayersParcelable?> {
            return arrayOfNulls(size)
        }
    }
}