package com.vioside.foundation.base.menu.adapters

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.menu_item.view.*

class MenuItemViewHolder(
    private val view: ConstraintLayout
) : RecyclerView.ViewHolder(view){

    val titleTextView: TextView = view.titleTextView
    fun bind(position: Int, listener: MenuItemListener) {
        view.setOnClickListener {
            listener.menuItemPressed(position)
        }
    }
}