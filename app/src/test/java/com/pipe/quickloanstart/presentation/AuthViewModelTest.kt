package com.pipe.quickloan.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pipe.quickloan.MainDispatcherRule
import com.pipe.quickloan.data.models.TokenEntity
import com.pipe.quickloan.di.BaseResult
import com.pipe.quickloan.domain.usecases.LoginUseCase
import com.pipe.quickloan.presentation.base.State
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class AuthViewModelTest {

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var loginUseCase: LoginUseCase

    @Mock
    private lateinit var authViewModel: AuthViewModel

    private val any = "any"
    private val response = "111"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        authViewModel = AuthViewModel(loginUseCase)
    }

    @Test
    fun `init State result State Init`() = runBlocking {
        val actualInit = State.Init
        val expected = authViewModel.state.value
        assertEquals(actualInit, expected)
    }

    @Test
    fun `when start return IsLoading(true)`() = runBlocking {
        val tokenEntity = TokenEntity(response)
        val flow = flow<BaseResult<TokenEntity, Throwable>> { BaseResult.Success(tokenEntity) }
        whenever(loginUseCase.invoke(any())).thenReturn(flow)
        authViewModel.start(any, any)
        val actual = authViewModel.state.value
        val expected = State.IsLoading(true)
        assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return TokenEntity result Success`() = runBlocking {
        val tokenEntity = TokenEntity(response)
        val flow = flow<BaseResult<TokenEntity, Throwable>> {
            emit(BaseResult.Success(tokenEntity))
        }
        whenever(loginUseCase.invoke(any())).thenReturn(flow)
        authViewModel.start(any, any)
        val actual = authViewModel.state.value
        val expected = State.Success(tokenEntity)
        assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return Error`() = runBlocking {
        val flow = flow<BaseResult<TokenEntity, Throwable>> {
            emit(BaseResult.Errors(Throwable("Errors")))
        }
        flow.collect {
            println(it)
        }
        whenever(loginUseCase.invoke(any())).thenReturn(flow)
        authViewModel.start(any, any)
        val actual = authViewModel.state.value.toString()
        val expected = State.Error(Throwable("Errors")).toString()
        assertEquals(expected, actual)
    }
}
