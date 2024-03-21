package com.vioside.foundation.base.menu.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vioside.foundation.base.menu.viewModels.MenuViewModel
import com.vioside.foundation.base.menu.models.MenuItemModel

@BindingAdapter(value = ["menuItems", "viewModel"], requireAll = false)
fun loadMenu(recyclerView: RecyclerView,
             menuItems: ArrayList<MenuItemModel>?,
             viewModel: MenuViewModel
) {
    menuItems?.let {
        val adapter = MenuRecyclerAdapter(menuItems, viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            recyclerView.context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
}