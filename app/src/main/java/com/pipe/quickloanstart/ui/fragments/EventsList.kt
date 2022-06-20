package com.pipe.quickloanstart.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pipe.quickloanstart.R
import com.pipe.quickloanstart.di.NavigatorModule
import com.pipe.quickloanstart.presentation.EventsViewModel
import com.pipe.quickloanstart.ui.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EventsList : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_all_events

    private val viewModel: EventsViewModel by viewModels()

    @Inject
    lateinit var navigatorModule: NavigatorModule

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}