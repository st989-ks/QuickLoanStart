package com.pipe.quickloanstart.presentation.base

sealed class State {
    object Init : State()
    data class IsLoading(val isLoading: Boolean) : State()
    data class Error(val throwable: Throwable) : State()
    data class Success(val success: Any) : State()
    data class ShowToast(val message: String) : State()
}