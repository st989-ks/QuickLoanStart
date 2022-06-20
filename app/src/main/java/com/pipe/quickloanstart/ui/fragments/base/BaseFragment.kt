package com.pipe.quickloanstart.ui.fragments.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pipe.quickloanstart.extensions.APP_TAG
import com.pipe.quickloanstart.extensions.showLoader


abstract class BaseFragment: Fragment() {

    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    protected fun handleShow(message: String) {
        Log.e(APP_TAG, message)
    }

    protected fun handleLoading(isLoading: Boolean) {
        requireActivity().showLoader(isLoading)
    }
}