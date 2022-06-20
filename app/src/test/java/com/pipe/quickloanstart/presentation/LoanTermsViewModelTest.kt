package com.pipe.quickloan.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pipe.quickloan.MainDispatcherRule
import com.pipe.quickloan.data.models.TermsEntity
import com.pipe.quickloan.di.BaseResult
import com.pipe.quickloan.domain.usecases.TermsUseCase
import com.pipe.quickloan.presentation.base.State
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class LoanTermsViewModelTest {

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var termsUseCase: TermsUseCase

    @Mock
    private lateinit var loanTermsViewModel: LoanTermsViewModel

    private val anyString = "any"
    private val anyInt = 0
    private val termsEntity = TermsEntity(
        maxAmount = anyInt,
        percent = anyInt.toDouble(),
        period = anyInt,
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loanTermsViewModel = LoanTermsViewModel(termsUseCase)
    }

    @Test
    fun `init State result State Init`() = runBlocking {
        val actualInit = State.Init
        val expected = loanTermsViewModel.state.value
        Assertions.assertEquals(actualInit, expected)
    }

    @Test
    fun `when start return IsLoading(true)`() = runBlocking {
        val flow = flow<BaseResult<TermsEntity, Throwable>> { BaseResult.Success(termsEntity) }
        whenever(termsUseCase.invoke()).thenReturn(flow)
        loanTermsViewModel.start()
        val actual = loanTermsViewModel.state.value
        val expected = State.IsLoading(true)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return TokenEntity result Success(token)`() = runBlocking {
        val flow = flow<BaseResult<TermsEntity, Throwable>> {
            emit(BaseResult.Success(termsEntity))
        }
        whenever(termsUseCase.invoke()).thenReturn(flow)
        loanTermsViewModel.start()
        val actual = loanTermsViewModel.state.value
        val expected = State.Success(termsEntity)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return Error`() = runBlocking {
        val flow = flow<BaseResult<TermsEntity, Throwable>> {
            emit(BaseResult.Errors(Throwable("Errors")))
        }
        flow.collect {
            println(it)
        }
        whenever(termsUseCase.invoke()).thenReturn(flow)
        loanTermsViewModel.start()
        val actual = loanTermsViewModel.state.value.toString()
        val expected = State.Error(Throwable("Errors")).toString()
        Assertions.assertEquals(expected, actual)
    }
}
