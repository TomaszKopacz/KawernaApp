package com.tomaszkopacz.kawernaapp.functionalities.newgame

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Categories
import com.tomaszkopacz.kawernaapp.data.Score
import com.tomaszkopacz.kawernaapp.extensions.setCursorToEnd
import com.tomaszkopacz.kawernaapp.functionalities.newgame.ScoresAdapter.ScoresViewHolder
import kotlinx.android.synthetic.main.score_item.view.*

class ScoresAdapter : RecyclerView.Adapter<ScoresViewHolder>() {
    companion object {
        private const val TAG = "Kawerna"
    }

    private var scores: List<Score> = ArrayList()
    private var category: Int = Categories.LIVESTOCK

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

    fun loadCategory(category: Int) {
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
            itemView.player.text = value.player.email
        }

        private fun setCurrentScore(score: Score) {
            var categoryScore: String = when (category) {
                Categories.LIVESTOCK -> score.livestock.toString()
                Categories.LIVESTOCK_LACK -> score.livestockLack.toString()
                Categories.CEREAL -> score.cereal.toString()
                Categories.VEGETABLES -> score.vegetables.toString()
                Categories.RUBIES -> score.rubies.toString()
                Categories.DWARFS -> score.dwarfs.toString()
                Categories.AREAS -> score.areas.toString()
                Categories.UNUSED_AREAS -> score.unusedAreas.toString()
                Categories.PREMIUM_AREAS -> score.premiumAreas.toString()
                Categories.GOLD -> score.gold.toString()
                Categories.BEGS -> score.begs.toString()
                else -> ""
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