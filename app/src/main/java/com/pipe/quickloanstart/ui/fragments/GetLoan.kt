package com.pipe.quickloanstart.ui.fragments

import android.os.Bundle
import android.view.View
import com.pipe.quickloanstart.R
import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.data.models.TermsEntity
import com.pipe.quickloanstart.di.NavigatorModule
import com.pipe.quickloanstart.extensions.SharedPrefs
import com.pipe.quickloanstart.extensions.ThrowableReturnText
import com.pipe.quickloanstart.extensions.getLocaleStringResource
import com.pipe.quickloanstart.extensions.showToast
import com.pipe.quickloanstart.presentation.LoanTermsViewModel
import com.pipe.quickloanstart.presentation.NewLoanViewModel
import com.pipe.quickloanstart.presentation.base.State
import com.pipe.quickloanstart.ui.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_get_loan.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class GetLoan : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_get_loan

    @Inject
    lateinit var loanTermsViewModel: LoanTermsViewModel

    @Inject
    lateinit var newLoanViewModel: NewLoanViewModel

    @Inject
    lateinit var navigatorModule: NavigatorModule

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    private var percent: Double = 0.0
    private var period = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonInit()
        observe()
        updateTerms()
    }

    private fun observe() {
        loanTermsViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        newLoanViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
    }

    private fun buttonInit() {
        btn_request_loan.setOnClickListener { requestLoan() }
        terms.setOnClickListener { updateTerms() }
    }

    private fun requestLoan() {
        val amount = try {
            edit_amount.text.toString().toInt()
        } catch (nfe: NumberFormatException) {
            0
        }

        newLoanViewModel.start(
            amount = amount,
            firstName = edit_firstName.text.toString(),
            lastName = edit_lastName.text.toString(),
            percent = percent,
            period = period,
            phoneNumber = edit_phoneNumber.text.toString()
        )
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
            is TermsEntity -> {
                percent = data.percent
                period = data.period
                val terms = requireContext().getString(
                    R.string.amount_percent_for_fragment,
                    data.maxAmount.toString(),
                    data.percent.toString(),
                    data.period.toString()
                )
                list_of_loan_terms.text = terms
            }
            is LoanEntity -> {
                if (sharedPrefs.getOnBackPressed()) {
                    sharedPrefs.setOnBackPressed(false)
                    navigatorModule.navigateBack()
                }else{
                    sharedPrefs.setIdLoan(data.id)
                    navigatorModule.navigate(R.id.nav_loan_status)
                }
            }
        }
    }

    private fun updateTerms() {
        if (!sharedPrefs.getOnBackPressed()) loanTermsViewModel.start()
    }
}