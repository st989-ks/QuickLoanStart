package com.pipe.quickloanstart.presentation

import androidx.lifecycle.viewModelScope
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.usecases.LoansListUseCase
import com.pipe.quickloanstart.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoansListViewModel @Inject constructor(
    private val loansListUseCase: LoansListUseCase,
) :  BaseViewModel(){

    fun start() {
        viewModelScope.launch {
            loansListUseCase.invoke()
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

