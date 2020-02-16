package com.tomaszkopacz.kawernaapp.ui.game.players

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Player
import java.util.*
import kotlin.collections.ArrayList

class PlayersAdapter : RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    private var players: List<Player> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PlayerViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.player_item, parent, false)

        return PlayerViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        val name = player.name
        val text = name.subSequence(0, 2).toString().toUpperCase(Locale.getDefault())

        holder.setPlayerView(text)
    }

    fun loadPlayers(players: ArrayList<Player>) {
        this.players = players
        notifyDataSetChanged()
    }

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var playerView = itemView.findViewById<TextView>(R.id.player_text)

        fun setPlayerView(text: String) {
            playerView.text = text
        }
    }
}