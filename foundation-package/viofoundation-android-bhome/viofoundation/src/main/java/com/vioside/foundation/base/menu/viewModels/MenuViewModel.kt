package com.vioside.foundation.base.menu.viewModels

import androidx.lifecycle.MutableLiveData
import com.vioside.foundation.base.BaseViewModel
import com.vioside.foundation.base.menu.models.MenuItemModel

open class MenuViewModel : BaseViewModel() {

    val menuItems = MutableLiveData(arrayListOf<MenuItemModel>())

    fun menuItemPressed(index: Int) {
        menuItems.value?.getOrNull(index)?.let {
            it.destination?.let { direction ->
                navigate(direction)
            }
            it.action?.let { action ->
                action.invoke(index, it)
            }
        }
    }

}