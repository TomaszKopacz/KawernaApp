package com.tomaszkopacz.kawernaapp.functionalities.main.statistics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.ScoreCategory

class CategoryAdapter(context: Context
) : ArrayAdapter<ScoreCategory>(context, 0, ScoreCategory.values()){

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: layoutInflater.inflate(R.layout.category_item, parent, false)

        getItem(position)?.let {category ->
            setItemForCategory(view, category)
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: layoutInflater.inflate(R.layout.category_item, parent, false)

        getItem(position)?.let {category ->
            setItemForCategory(view, category)
        }

        return view
    }

    private fun setItemForCategory(view: View, category: ScoreCategory) {
        val tvCategory = view.findViewById<TextView>(R.id.tvCategory)
        tvCategory.text = context.resources.getString(context.resources.getIdentifier(category.name, "string", context.packageName))
    }
}