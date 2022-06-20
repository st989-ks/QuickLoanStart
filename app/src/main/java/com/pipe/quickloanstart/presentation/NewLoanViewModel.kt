package com.pipe.quickloanstart.presentation

import androidx.lifecycle.viewModelScope
import com.pipe.quickloanstart.data.models.NewLoanRequest
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.usecases.NewLoanUseCase
import com.pipe.quickloanstart.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewLoanViewModel @Inject constructor(
    private val newLoanUseCase: NewLoanUseCase,
) :  BaseViewModel()  {

    fun start(
        amount: Int,
        firstName: String,
        lastName: String,
        percent: Double,
        period: Int,
        phoneNumber: String,
    ) {
        val send = NewLoanRequest(
            amount = amount,
            firstName = firstName,
            lastName = lastName,
            percent = percent,
            period = period,
            phoneNumber = phoneNumber
        )
        viewModelScope.launch {
            newLoanUseCase.invoke(send)
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

