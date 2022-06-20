package com.pipe.quickloanstart.ui.fragments
import android.os.Bundle
import android.view.View
import com.pipe.quickloanstart.R
import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.di.NavigatorModule
import com.pipe.quickloanstart.extensions.*
import com.pipe.quickloanstart.presentation.LoanStatusViewModel
import com.pipe.quickloanstart.presentation.base.State
import com.pipe.quickloanstart.ui.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_loan_status.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LoanStatus : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_loan_status

    @Inject
    lateinit var loanStatusViewModel: LoanStatusViewModel

    @Inject
    lateinit var navigatorModule: NavigatorModule

    @Inject
    lateinit var sharedPrefs: SharedPrefs


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        updateTerms()
        initButton()
    }

    private fun initButton() {
        this.hideKeyboard()
        btn_close_status.setOnClickListener {
            sharedPrefs.setOnBackPressed(true)
            navigatorModule.navigateBack()
        }
    }

    private fun updateTerms() {
        val id = sharedPrefs.getIdLoan()
        loanStatusViewModel.start(id)
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

    private fun observe() {
        loanStatusViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
    }

    private fun handleError(error: Throwable) {
        val local = Locale(sharedPrefs.getLanguage())
        val text = requireActivity()
            .getLocaleStringResource(local, ThrowableReturnText().foundThrowable(error))
        requireActivity().showToast(text)
    }


    private fun handleSuccess(data: Any) {
        when (data) {
            is LoanEntity -> {

                val status = requireActivity().getString(
                    R.string.loan_status_for_fragment,
                    requireActivity().getString(data.state.getStatus())
                )

                val info = requireActivity().getString(
                    R.string.all_date_loan_for_fragment,
                    data.date.formatDate(),
                    data.amount.toString(),
                    data.period.toString(),
                    data.percent.toString(),
                    data.firstName.toString(),
                    data.lastName.toString(),
                    data.id.toString(),
                    data.phoneNumber.toString(),
                )

                loans_status_all.apply {
                    text = status
                    textColorStatus(data.state)
                }
                list_of_loans_status.text = info
                sharedPrefs.toZeroIdLoan()
            }
        }
    }
}