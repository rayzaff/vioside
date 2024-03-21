package com.vioside.foundation.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.aslp.komunikapp.foundation.R
import com.kaopiz.kprogresshud.KProgressHUD
import com.vioside.foundation.helpers.NavigationCommand

abstract class BaseDialogFragment : DialogFragment() {

    abstract val viewModel: BaseViewModel

    private var progressHud: KProgressHUD? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        retainInstance = true

        observeNavigation(viewModel)
    }

    override fun onDestroyView() {
        val dialog: Dialog? = dialog
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && retainInstance) {
            dialog.setDismissMessage(null)
        }
        super.onDestroyView()
    }

    fun observeError() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                showError(it.title, it.message)
            }
        })
    }

    fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })
    }

    fun observeKeyboardHidden() {
        viewModel.closeKeyboard.observe(viewLifecycleOwner, Observer {
            hideKeyboard()
        })
    }

    fun observeDismissed() {
        viewModel.dismissed.observe(viewLifecycleOwner, Observer {
            if(it) {
                activity?.finish()
            }
        })
    }

    fun observeBackButton() {
        viewModel.goBack.observe(viewLifecycleOwner, Observer {
            if(it) {
                activity?.onBackPressed()
            }
        })
    }

    /**
     * Observe a [NavigationCommand] [Event] [LiveData].
     * When this [LiveData] is updated, [Fragment] will navigate to its destination
     */
    private fun observeNavigation(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            it?.let { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions, getExtras())
                    is NavigationCommand.Back -> findNavController().navigateUp()
                    is NavigationCommand.BackTo -> findNavController().popBackStack(command.destinationId, false)
                    is NavigationCommand.ToRoot -> Unit
                }
                viewModel.navigation.value = null
            }
        })
    }

    /**
     * [FragmentNavigatorExtras] mainly used to enable Shared Element transition
     */
    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

    fun showLoading(isLoading: Boolean) {
        if(progressHud == null && context != null) {
            progressHud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.loading))
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
        }
        if(isLoading) {
            progressHud?.show()
        } else {
            progressHud?.dismiss()
        }
    }

    fun showError(title: String?, error: String?) {
        context?.run {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(title ?: getString(R.string.whoops))
            builder.setMessage(error)
            builder.setNeutralButton(android.R.string.ok, null)
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()
        }
    }

    /*
    * Hiding Keyboard Functions
    * https://stackoverflow.com/a/45857155/2199589
    * */

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}