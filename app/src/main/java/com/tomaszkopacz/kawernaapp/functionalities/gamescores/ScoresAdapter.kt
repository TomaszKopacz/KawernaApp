package com.tomaszkopacz.kawernaapp.functionalities.gamescores

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.data.ScoreCategory
import com.tomaszkopacz.kawernaapp.extensions.setCursorToEnd
import com.tomaszkopacz.kawernaapp.functionalities.gamescores.ScoresAdapter.ScoresViewHolder
import kotlinx.android.synthetic.main.score_item.view.*

class ScoresAdapter : RecyclerView.Adapter<ScoresViewHolder>() {
    companion object {
        private const val TAG = "Kawerna"
    }

    private var scores: List<Score> = ArrayList()
    private var category: ScoreCategory = ScoreCategory.LIVESTOCK

    private var scoreWatcher: ScoreWatcher? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.score_item, parent, false)

        return ScoresViewHolder(view)
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        holder.score = scores[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setScoreWatcher(scoreTextWatcher: ScoreWatcher) {
        this.scoreWatcher = scoreTextWatcher
    }

    fun loadScores(scores: ArrayList<Score>) {
        this.scores = scores
        notifyDataSetChanged()
    }

    fun loadCategory(category: ScoreCategory) {
        this.category = category
    }

    inner class ScoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), TextWatcher {

        var score: Score? = null
            set(value) {
                field = value
                setItemFields(value)
            }

        private fun setItemFields(score: Score?) {
            if (score != null) {
                setPlayer(score)
                setCurrentScore(score)
                setTotalScore(score)
            }
        }

        private fun setPlayer(value: Score) {
            itemView.player.text = value.player
        }

        private fun setCurrentScore(score: Score) {
            var categoryScore: String = when (category) {
                ScoreCategory.LIVESTOCK -> score.livestock.toString()
                ScoreCategory.LIVESTOCK_LACK -> score.livestockLack.toString()
                ScoreCategory.CEREAL -> score.cereal.toString()
                ScoreCategory.VEGETABLES -> score.vegetables.toString()
                ScoreCategory.RUBIES -> score.rubies.toString()
                ScoreCategory.DWARFS -> score.dwarfs.toString()
                ScoreCategory.AREAS -> score.areas.toString()
                ScoreCategory.UNUSED_AREAS -> score.unusedAreas.toString()
                ScoreCategory.PREMIUM_AREAS -> score.premiumAreas.toString()
                ScoreCategory.GOLD -> score.gold.toString()
                ScoreCategory.BEG -> score.begs.toString()
            }

            if (categoryScore == "null") categoryScore = ""

            itemView.current_score.removeTextChangedListener(this)
            itemView.current_score.setText(categoryScore)
            itemView.current_score.setCursorToEnd()
            itemView.current_score.addTextChangedListener(this)
        }

        private fun setTotalScore(value: Score) {
            itemView.total_score.text = value.total().toString()
        }

        init {
            itemView.current_score.addTextChangedListener(this)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            if (scoreWatcher != null) {
                val result = try { text.toString().toInt() } catch (e: Exception) { 0 }
                scoreWatcher!!.onScoreChanged(adapterPosition, result)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            notifyDataSetChanged()
        }
    }
}