package com.pipe.quickloanstart.presentation

import androidx.lifecycle.viewModelScope
import com.pipe.quickloanstart.data.models.RegistrationRequest
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.usecases.RegistrationUseCase
import com.pipe.quickloanstart.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
) :  BaseViewModel()  {

    fun start(name: String, password: String) {
        val send = RegistrationRequest(name, password)
        viewModelScope.launch {
            registrationUseCase.invoke(send)
                .onStart {
                    setLoading()
                }.catch {
                    hideLoading()
                    errorReg(it)
                }.collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.Success -> successReg(it.data)
                        is BaseResult.Errors -> errorReg(it.error)
                    }
                }
        }
    }
}

