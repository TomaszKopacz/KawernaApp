package com.tomaszkopacz.kawernaapp.functionalities.home

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_score_item, parent, false)
        return ScoresViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        val score = scores[position]

        holder.setDate(score.date)
        holder.setTotal(score.total())
        holder.setPlace(score.place)
        holder.setPlayersCount(score.playersCount)
    }

    fun loadScores(scoresToLoad: ArrayList<Score>) {
        scores = scoresToLoad
        notifyDataSetChanged()
    }

    class ScoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var dateTextView = itemView.findViewById<TextView>(R.id.date)
        private var totalTextView = itemView.findViewById<TextView>(R.id.total)
        private var placeTextView = itemView.findViewById<TextView>(R.id.place)
        private var playersNumTextView = itemView.findViewById<TextView>(R.id.playersNum)

        fun setDate(date: String?) {
            dateTextView.text = date ?: "no date"
        }

        fun setTotal(total: Int) {
            totalTextView.text = total.toString()
        }

        fun setPlace(place: Int?) {
            placeTextView.text = place?.toString() ?: "no place"
        }

        fun setPlayersCount(playersCount: Int?) {
            playersNumTextView.text = playersCount?.toString() ?: "no players count"
        }
    }
}