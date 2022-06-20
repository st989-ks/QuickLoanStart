
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class NewLoanViewModelTest {

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var newLoanUseCase: NewLoanUseCase

    @Mock
    private lateinit var newLoanViewModel: NewLoanViewModel

    private val anyString = "any"
    private val anyInt = 0
    private val loanEntity = LoanEntity(
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

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        newLoanViewModel = NewLoanViewModel(newLoanUseCase)
    }

    @Test
    fun `init State result State Init`() = runBlocking {
        val actualInit = State.Init
        val expected = newLoanViewModel.state.value
        Assertions.assertEquals(actualInit, expected)
    }

    @Test
    fun `when start return IsLoading(true)`() = runBlocking {
        val flow = flow<BaseResult<LoanEntity, Throwable>> { BaseResult.Success(loanEntity) }
        whenever(newLoanUseCase.invoke(any())).thenReturn(flow)
        newLoanViewModel.start(
            amount = anyInt,
            firstName = anyString,
            lastName = anyString,
            percent = anyInt.toDouble(),
            period = anyInt,
            phoneNumber = anyString,
        )
        val actual = newLoanViewModel.state.value
        val expected = State.IsLoading(true)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return TokenEntity result Success(token)`() = runBlocking {
        val flow = flow<BaseResult<LoanEntity, Throwable>> {
            emit(BaseResult.Success(loanEntity))
        }
        whenever(newLoanUseCase.invoke(any())).thenReturn(flow)
        newLoanViewModel.start(
            amount = anyInt,
            firstName = anyString,
            lastName = anyString,
            percent = anyInt.toDouble(),
            period = anyInt,
            phoneNumber = anyString,
        )
        val actual = newLoanViewModel.state.value
        val expected = State.Success(loanEntity)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return Error`() = runBlocking {
        val flow = flow<BaseResult<LoanEntity, Throwable>> {
            emit(BaseResult.Errors(Throwable("Errors")))
        }
        flow.collect {
            println(it)
        }
        whenever(newLoanUseCase.invoke(any())).thenReturn(flow)
        newLoanViewModel.start(
            amount = anyInt,
            firstName = anyString,
            lastName = anyString,
            percent = anyInt.toDouble(),
            period = anyInt,
            phoneNumber = anyString,
        )
        val actual = newLoanViewModel.state.value.toString()
        val expected = State.Error(Throwable("Errors")).toString()
        Assertions.assertEquals(expected, actual)
    }
}
