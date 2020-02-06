package com.tomaszkopacz.kawernaapp.functionalities.game.scores

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.PlayerScore
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.utils.extensions.setCursorToEnd
import com.tomaszkopacz.kawernaapp.functionalities.game.scores.ScoresAdapter.ScoresViewHolder
import kotlinx.android.synthetic.main.score_item.view.*

class ScoresAdapter : RecyclerView.Adapter<ScoresViewHolder>() {

    private var playersScores: List<PlayerScore> = ArrayList()
    private var category: ScoreCategory = ScoreCategory.ANIMALS

    private var scoreWatcher: ScoreWatcher? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.score_item, parent, false)

        return ScoresViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playersScores.size
    }

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        holder.score = playersScores[position].score
        holder.player = playersScores[position].player
        holder.fillViewHolderFields()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setScoreWatcher(scoreTextWatcher: ScoreWatcher) {
        this.scoreWatcher = scoreTextWatcher
    }

    fun loadScores(playersScores: ArrayList<PlayerScore>) {
        this.playersScores = playersScores
        notifyDataSetChanged()
    }

    fun loadCategory(category: ScoreCategory) {
        this.category = category
    }

    inner class ScoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), TextWatcher {

        var player: Player? = null
        var score: Score? = null

        init {
            itemView.current_score.addTextChangedListener(this)
        }

        fun fillViewHolderFields() {
            if (score != null && player != null) {
                setPlayerText(player!!)
                setCurrentScoreText(score!!)
                setTotalScoreText(score!!)
            }
        }

        private fun setPlayerText(player: Player) {
            itemView.player.text = player.name
        }

        private fun setCurrentScoreText(score: Score) {
            var categoryScore: String = when (category) {
                ScoreCategory.TOTAL -> ""
                ScoreCategory.ANIMALS -> score.livestock.toString()
                ScoreCategory.ANIMALS_LACK -> score.livestockLack.toString()
                ScoreCategory.CEREAL -> score.cereal.toString()
                ScoreCategory.VEGETABLES -> score.vegetables.toString()
                ScoreCategory.RUBIES -> score.rubies.toString()
                ScoreCategory.DWARFS -> score.dwarfs.toString()
                ScoreCategory.AREAS -> score.areas.toString()
                ScoreCategory.UNUSED_AREAS -> score.unusedAreas.toString()
                ScoreCategory.PREMIUM_AREAS -> score.premiumAreas.toString()
                ScoreCategory.GOLD -> score.gold.toString()
            }

            if (categoryScore == "null") categoryScore = ""

            itemView.current_score.removeTextChangedListener(this)
            itemView.current_score.setText(categoryScore)
            itemView.current_score.setCursorToEnd()
            itemView.current_score.addTextChangedListener(this)
        }

        private fun setTotalScoreText(value: Score) {
            itemView.total_score.text = value.total().toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            if (scoreWatcher != null) {
                val result = try {
                    text.toString().toInt()
                } catch (e: Exception) {
                    0
                }
                scoreWatcher!!.onScoreChanged(adapterPosition, result)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            notifyDataSetChanged()
        }
    }
}