package com.vioside.foundation.base.menu.models

import androidx.navigation.NavDirections

data class MenuItemModel(
    var title: String,
    var destination: NavDirections? = null,
    var action: ((index: Int, menuItem: MenuItemModel) -> Unit)? = null
)