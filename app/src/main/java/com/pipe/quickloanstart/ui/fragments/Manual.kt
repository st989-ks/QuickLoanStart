package com.pipe.quickloanstart.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import com.pipe.quickloanstart.R
import com.pipe.quickloanstart.di.NavigatorModule
import com.pipe.quickloanstart.extensions.SharedPrefs
import com.pipe.quickloanstart.extensions.hideKeyboard
import com.pipe.quickloanstart.extensions.manualData
import com.pipe.quickloanstart.ui.adapter.ManualAdapter
import com.pipe.quickloanstart.ui.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_manual.*
import javax.inject.Inject

@AndroidEntryPoint
class Manual : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_manual

    @Inject
    lateinit var navigatorModule: NavigatorModule

    @Inject
    lateinit var manualAdapter: ManualAdapter

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefs.setNoFirstEntry(true)
        initListManual()
        initButton()
    }

    private fun initButton() {
        this.hideKeyboard()
        btn_close.setOnClickListener { navigatorModule.navigateBack() }
    }

    private fun initListManual() {
        PagerSnapHelper().attachToRecyclerView(recycler_manual_list)
        recycler_manual_list.apply {
            recycledViewPool.setMaxRecycledViews(0, 0)
            manualAdapter.listManual = manualData
            adapter = manualAdapter
        }
    }
}