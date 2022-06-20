package com.pipe.quickloanstart.presentation

import androidx.lifecycle.viewModelScope
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.usecases.LoanStatusUseCase
import com.pipe.quickloanstart.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanStatusViewModel @Inject constructor(
    private val loanStatusUseCase: LoanStatusUseCase,
) :  BaseViewModel() {

    fun start(id: Int) {
        viewModelScope.launch {
            loanStatusUseCase.invoke(id)
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

