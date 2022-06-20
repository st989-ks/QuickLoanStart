package com.pipe.quickloanstart.ui.fragments
import android.os.Bundle
import android.view.View
import com.pipe.quickloanstart.R
import com.pipe.quickloanstart.data.models.RegistrationEntity
import com.pipe.quickloanstart.data.models.TokenEntity
import com.pipe.quickloanstart.di.NavigatorModule
import com.pipe.quickloanstart.extensions.*
import com.pipe.quickloanstart.presentation.AuthViewModel
import com.pipe.quickloanstart.presentation.RegistrationViewModel
import com.pipe.quickloanstart.presentation.base.State
import com.pipe.quickloanstart.ui.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class Login : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_login

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var authViewModel: AuthViewModel

    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    @Inject
    lateinit var navigatorModule: NavigatorModule

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonInitial()
        observe()
    }

    private fun buttonInitial() {
        btn_registration.setOnClickListener { registrationName() }
        btn_sign_in.setOnClickListener { startLoans() }
    }

    private fun registrationName() {
        sharedPrefs.clearToken()
        registrationViewModel.start(
            edit_text_username.text.toString(),
            edit_text_password.text.toString()
        )
    }

    private fun startLoans() {
        sharedPrefs.clearToken()
        authViewModel.start(
            edit_text_username.text.toString(),
            edit_text_password.text.toString()
        )
    }

    private fun observe() {
        authViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        registrationViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
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

    private fun handleSuccess(data: Any) {
        when (data) {
            is RegistrationEntity -> {
                requireActivity().showGenericAlertDialog(
                    requireActivity().getString(
                        R.string.registration_user_message,
                        data.name, data.role,
                    )
                )
            }
            is TokenEntity -> {
                sharedPrefs.saveToken(data.token)
                if (data.token.isNotEmpty()) navigatorModule.navigateBack()
            }
        }
    }

    private fun handleError(error: Throwable) {
        val local = Locale(sharedPrefs.getLanguage())
        val text = requireActivity()
            .getLocaleStringResource(local, ThrowableReturnText().foundThrowable(error))
        requireActivity().showToast(text)
    }
}