package com.vioside.foundation.base.menu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.aslp.komunikapp.foundation.R
import com.vioside.foundation.base.menu.viewModels.MenuViewModel
import com.vioside.foundation.base.menu.models.MenuItemModel

interface MenuItemListener {
    fun menuItemPressed(index: Int)
}

class MenuRecyclerAdapter (
    private var menuItems: ArrayList<MenuItemModel>,
    private var viewModel: MenuViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>(), MenuItemListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MenuItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.menu_item, parent, false) as ConstraintLayout
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as MenuItemViewHolder
        viewHolder.bind(position, this)
        viewHolder.titleTextView.text = menuItems[position].title
    }

    override fun getItemCount(): Int{
        return menuItems.size
    }

    override fun menuItemPressed(index: Int) {
        viewModel.menuItemPressed(index)
    }

}