package com.vioside.foundation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.vioside.foundation.models.ErrorMessage
import com.vioside.foundation.helpers.NavigationCommand

abstract class BaseViewModel : ViewModel() {

    var error = MutableLiveData<ErrorMessage>()
    var isLoading = MutableLiveData<Boolean>()
    var dismissed = MutableLiveData<Boolean>()
    var goBack = MutableLiveData<Boolean>()
    var closeKeyboard = MutableLiveData<Boolean>()
    var saved = MutableLiveData<Boolean>()
    val navigation: MutableLiveData<NavigationCommand> = MutableLiveData<NavigationCommand>()
    var requestPermissionForAction = MutableLiveData<()->Unit>()

    /**
     * navigation from a [ViewModel]
     */
    fun navigate(directions: NavDirections) {
        navigation.value = NavigationCommand.To(directions)
    }
    /**
     * navigation from a [ViewModel] when in a background [Thread] like [Runnable]
     * or [java.util.Timer]
     */
    fun navigateFromBackgroundThread(directions: NavDirections) {
        navigation.value = NavigationCommand.To(directions)
    }
    /**
     * navigation from a [ViewModel]
     */
    fun navigateBack(destinationId: Int) {
        navigation.value = NavigationCommand.BackTo(destinationId)
    }

    open fun navigateBack() {
        navigation.value = NavigationCommand.Back
    }


}