package com.pipe.quickloanstart.presentation

import androidx.lifecycle.viewModelScope
import com.pipe.quickloanstart.data.models.LoginRequest
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.usecases.LoginUseCase
import com.pipe.quickloanstart.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {

    fun start(name: String, password: String) {
        val send = LoginRequest(name, password)
        viewModelScope.launch {
            loginUseCase.invoke(send)
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

