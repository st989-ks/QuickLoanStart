
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.flow.collectLatest
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

class NewLoanUseCaseTest {

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var useCase: NewLoanUseCase

    @Mock
    private lateinit var repository: NewLoanRepository


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
    private val loanRequest = NewLoanRequest(
        amount = anyInt,
        firstName = anyString,
        lastName = anyString,
        percent = anyInt.toDouble(),
        period = anyInt,
        phoneNumber = anyString,
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = NewLoanUseCase(repository)
    }

    @Test
    fun `when execute usecase return success response`() = runBlocking {
        val flow = flow<BaseResult<LoanEntity, Throwable>> {
            emit(BaseResult.Success(loanEntity))
        }
        whenever(repository.invoke(any())).thenReturn(flow)
        val expected = BaseResult.Success(loanEntity)
        useCase.invoke(loanRequest).collectLatest {
            Assertions.assertEquals(expected, it)
        }
    }

    @Test
    fun `when execute usecase return error response`() = runBlocking {
        val flow = flow<BaseResult<LoanEntity, Throwable>> {
            emit(BaseResult.Errors(Throwable()))
        }
        whenever(repository.invoke(any())).thenReturn(flow)
        val expected = BaseResult.Errors(Throwable())
        useCase.invoke(loanRequest).collectLatest {
            Assertions.assertEquals(expected.toString(), it.toString())
        }
    }
}