package com.vioside.viofoundation.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vioside.foundation.base.BaseFragment
import com.vioside.viofoundation.R
import com.vioside.viofoundation.databinding.FragmentExampleBinding
import com.vioside.viofoundation.sample.viewModels.SampleViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ExampleFragment: BaseFragment() {

    override val viewModel by viewModel<SampleViewModel>()

    private var binding: FragmentExampleBinding? = null
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = prepareBinding(inflater, container)
        rootView = binding?.root

        loadDataAndObservers()
        initializeViews()


        return rootView
    }

    private fun loadDataAndObservers() {
    }

    private fun initializeViews(){
    }

    private fun prepareBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExampleBinding {

        return DataBindingUtil.inflate<FragmentExampleBinding>(
            inflater,
            R.layout.fragment_example, container, false
        )
            .also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
            }
    }

}