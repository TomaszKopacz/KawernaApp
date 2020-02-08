package com.tomaszkopacz.kawernaapp.ui.main.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Score

class ScoresAdapter : RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder>() {

    private var scores: List<Score> = ArrayList()

    private var itemListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_score_item, parent, false)
        return ScoresViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        val score = scores[position]

        holder.setTotal(score.total())
    }

    fun loadScores(scoresToLoad: ArrayList<Score>) {
        scores = scoresToLoad
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        this.itemListener = listener
    }

    inner class ScoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var totalTextView = itemView.findViewById<TextView>(R.id.total)
        private var detailsButton = itemView.findViewById<Button>(R.id.details)

        init {
            setOnClickListener()
        }

        private fun setOnClickListener() {
            detailsButton.setOnClickListener {
                val position = adapterPosition
                val score = scores[position]
                itemListener?.onItemClick(score)
            }
        }

        fun setTotal(total: Int) {
            totalTextView.text = total.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(score: Score)
    }
}