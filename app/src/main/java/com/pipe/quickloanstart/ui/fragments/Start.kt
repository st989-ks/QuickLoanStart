package com.pipe.quickloanstart.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.pipe.quickloanstart.R
import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.di.NavigatorModule
import com.pipe.quickloanstart.extensions.*
import com.pipe.quickloanstart.presentation.LoansListViewModel
import com.pipe.quickloanstart.presentation.base.State
import com.pipe.quickloanstart.ui.adapter.LoansListAdapter
import com.pipe.quickloanstart.ui.fragments.base.BaseFragment
import com.pipe.quickloanstart.ui.push.SuperWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fab_layout.*
import kotlinx.android.synthetic.main.fragment_start.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class Start : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_start

    @Inject
    lateinit var loansListViewModel: LoansListViewModel

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var loansListAdapter: LoansListAdapter

    @Inject
    lateinit var navigatorModule: NavigatorModule

    private var isFABOpen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabLayoutInit()
        buttonInit()
        observe()
        startData()
    }

    private fun setLocal() {
        if (sharedPrefs.getLanguage() == getString(R.string.language_ru)) {
            sharedPrefs.setLanguage(getString(R.string.language_en))
        } else {
            sharedPrefs.setLanguage(getString(R.string.language_ru))
        }
        openChangeLanguageDialog()
    }

    private fun openChangeLanguageDialog() {
        ChangeLanguageDialog(requireActivity(), language = sharedPrefs.getLanguage()).apply {
            show()
            onSave = {
                dismiss()
                requireActivity().recreate()
                sharedPrefs.setLanguage(it)
            }
        }
    }

    private fun fabLayoutInit() {
        fab_big.setOnClickListener { if (!isFABOpen) showFABMenu() else closeFABMenu() }
    }

    //TODO()
    private fun enqueueWorker() {
        val request = OneTimeWorkRequestBuilder<SuperWorker>().build()
        WorkManager.getInstance(requireActivity()).enqueue(request)
    }

    private fun buttonInit() {
        image_view_loan.setOnClickListener{ enqueueWorker()}    //TODO()
        this.hideKeyboard()
        btn_personal_loan.setOnClickListener { navigatorModule.navigate(R.id.nav_getLoan) }
        fab_events.setOnClickListener { navigatorModule.navigate(R.id.nav_all_events) }
        fab_relogin.setOnClickListener { resetAuthorization() }
        fab_update_the_list.setOnClickListener { loansListViewModel.start() }
        fab_manual.setOnClickListener { navigatorModule.navigate(R.id.nav_manual) }
        fab_language.setOnClickListener { setLocal() }
        loansListAdapter.setOnContainerClickListener { loan -> viewLoan(loan) }
    }

    private fun viewLoan(loan: LoanEntity) {
        sharedPrefs.setIdLoan(loan.id)
        navigatorModule.navigate(R.id.nav_loan_status)
    }

    private fun observe() {
        loansListViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
    }

    private fun startData() {
        if (sharedPrefs.getToken().isEmpty()) {
            navigatorModule.navigate(R.id.nav_login)
        } else if (!sharedPrefs.getNoFirstEntry()) {
            navigatorModule.navigate(R.id.nav_manual)
        } else {
            loansListViewModel.start()
            sharedPrefs.setOnBackPressed(false)
        }
    }

    private fun handleState(state: State) {
        when (state) {
            is State.ShowToast -> handleShow(state.message)
            is State.IsLoading -> handleLoading(state.isLoading)
            is State.Error -> handleError(state.throwable)
            is State.Success -> handleSuccess(state.success)
            is State.Init -> Unit
        }
    }

    private fun handleError(error: Throwable) {
        val local = Locale(sharedPrefs.getLanguage())
        val text = requireActivity()
            .getLocaleStringResource(local, ThrowableReturnText().foundThrowable(error))
        requireActivity().showToast(text)
    }

    private fun handleSuccess(data: Any) {
        when (data) {
            is List<*> -> {
                val loans: List<LoanEntity> = data as List<LoanEntity>
                recycler_loans_list.apply {
                    loansListAdapter.loans = loans
                    adapter = loansListAdapter
                }

                val lastDate = loans.filter { it.state == APPROVED }.maxByOrNull { it.id }?.date

                lastDate?.let {
                    sharedPrefs.apply {
                        setTextPush(it.formatDate())
                        putSizeList(data.size)
                    }
                }
            }
        }
    }

    private fun resetAuthorization() {
        sharedPrefs.apply {
            setNoFirstEntry(false)
            clearToken()
        }
        navigatorModule.navigate(R.id.nav_login)
    }

    private fun closeFABMenu() {
        isFABOpen = false
        fab_big.animate().rotation(0F)

        fab_relogin.animate()
            .translationY(resources.getDimension(R.dimen.standard_position))
        fab_events.animate()
            .translationY(resources.getDimension(R.dimen.standard_position))
        fab_language.animate()
            .translationY(resources.getDimension(R.dimen.standard_position))
        fab_manual.animate()
            .translationX(resources.getDimension(R.dimen.standard_position))
            .translationY(resources.getDimension(R.dimen.standard_position))
        fab_update_the_list.animate()
            .translationX(resources.getDimension(R.dimen.standard_position))
    }

    private fun showFABMenu() {
        isFABOpen = true
        fab_relogin.visibility = View.VISIBLE
        fab_events.visibility = View.VISIBLE
        fab_update_the_list.visibility = View.VISIBLE
        fab_manual.visibility = View.VISIBLE
        fab_language.visibility = View.VISIBLE

        fab_big.animate()
            .rotationBy(resources.getDimension(R.dimen.standard_rotation))

        fab_relogin.animate()
            .translationY(-resources.getDimension(R.dimen.standard_btn_relogin))
        fab_events.animate()
            .translationY(-resources.getDimension(R.dimen.standard_btn_events))
        fab_language.animate()
            .translationY(-resources.getDimension(R.dimen.standard_btn_update_the_list))
        fab_manual.animate()
            .translationX(-resources.getDimension(R.dimen.standard_btn_fab_manual))
            .translationY(-resources.getDimension(R.dimen.standard_btn_fab_manual))
        fab_update_the_list.animate()
            .translationX(-resources.getDimension(R.dimen.standard_btn_fab_language))
    }


    override fun onPause() {
        super.onPause()
        isFABOpen = false
    }
}