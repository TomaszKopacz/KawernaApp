package com.tomaszkopacz.kawernaapp.functionalities.resultscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Score

class ScoresAdapter : RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder>() {

    private var scores: List<Score> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.result_score_item, parent, false)
        return ScoresViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        val score = scores[position]

        holder.setPlace(score.place)
        holder.setPlayer(score.player)
        holder.setScore(score.total().toString())
    }

    fun loadScores(scoresToLoad: ArrayList<Score>) {
        scores = scoresToLoad
        notifyDataSetChanged()
    }

    class ScoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var placeTextView = itemView.findViewById<TextView>(R.id.place)
        private var playerTextView = itemView.findViewById<TextView>(R.id.player)
        private var scoreTextView = itemView.findViewById<TextView>(R.id.score)

        fun setPlace(place: Int?) {
            placeTextView.text = place?.toString() ?: "no place"
        }

        fun setPlayer(player: String) {
            playerTextView.text = player
        }

        fun setScore(score: String) {
            scoreTextView.text = score
        }

    }

}