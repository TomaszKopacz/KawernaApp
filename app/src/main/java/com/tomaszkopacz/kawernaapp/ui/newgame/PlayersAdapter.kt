package com.tomaszkopacz.kawernaapp.ui.newgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Player

class PlayersAdapter : RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    private var players: List<Player> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PlayersAdapter.PlayerViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.player_item, parent, false)

        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayersAdapter.PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.setEmail(player.email)
    }

    fun loadPlayers(players: ArrayList<Player>) {
        this.players = players
        notifyDataSetChanged()
    }

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var emailView = itemView.findViewById<TextView>(R.id.email_view)

        fun setEmail(email: String) {
            emailView.text = email
        }
    }
}