package com.pipe.quickloan.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pipe.quickloan.MainDispatcherRule
import com.pipe.quickloan.data.models.LoanEntity
import com.pipe.quickloan.di.BaseResult
import com.pipe.quickloan.domain.usecases.LoansListUseCase
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


class LoansListViewModelTest {

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var loansListUseCase: LoansListUseCase

    @Mock
    private lateinit var loansListViewModel: LoansListViewModel

    private val anyString = "any"
    private val anyInt = 0
    private val list: List<LoanEntity> = listOf(
        LoanEntity(
            firstName = anyString,
            lastName = anyString,
            phoneNumber = anyString,
            amount = anyInt,
            percent = anyInt.toDouble(),
            period = anyInt,
            date = anyString,
            state = anyString,
            id = anyInt,
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loansListViewModel = LoansListViewModel(loansListUseCase)
    }

    @Test
    fun `init State result State Init`() = runBlocking {
        val actualInit = State.Init
        val expected = loansListViewModel.state.value
        Assertions.assertEquals(actualInit, expected)
    }

    @Test
    fun `when start return IsLoading(true)`() = runBlocking {
        val flow = flow<BaseResult<List<LoanEntity>, Throwable>> { BaseResult.Success(list) }
        whenever(loansListUseCase.invoke()).thenReturn(flow)
        loansListViewModel.start()
        val actual = loansListViewModel.state.value
        val expected = State.IsLoading(true)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return TokenEntity result Success`() = runBlocking {
        val flow = flow<BaseResult<List<LoanEntity>, Throwable>> {
            emit(BaseResult.Success(list))
        }
        whenever(loansListUseCase.invoke()).thenReturn(flow)
        loansListViewModel.start()
        val actual = loansListViewModel.state.value
        val expected = State.Success(list)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return Error`() = runBlocking {
        val flow = flow<BaseResult<List<LoanEntity>, Throwable>> {
            emit(BaseResult.Errors(Throwable("Errors")))
        }
        flow.collect {
            println(it)
        }
        whenever(loansListUseCase.invoke()).thenReturn(flow)
        loansListViewModel.start()
        val actual = loansListViewModel.state.value.toString()
        val expected = State.Error(Throwable("Errors")).toString()
        Assertions.assertEquals(expected, actual)
    }
}
