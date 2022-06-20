
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
import org.mockito.kotlin.whenever

class LoansListUseCaseTest{

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: LoansListUseCase

    @Mock
    private lateinit var repository: LoansListRepository


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
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = LoansListUseCase(repository)
    }

    @Test
    fun `when execute usecase return success response`() = runBlocking {
        val flow = flow<BaseResult<List<LoanEntity>, Throwable>> {
            emit(BaseResult.Success(list))
        }
        whenever(repository.invoke()).thenReturn(flow)
        val expected = BaseResult.Success(list)
        useCase.invoke().collectLatest {
            Assertions.assertEquals(expected, it)
        }
    }

    @Test
    fun `when execute usecase return error response`() = runBlocking {
        val flow = flow<BaseResult<List<LoanEntity>, Throwable>> {
            emit(BaseResult.Errors(Throwable()))
        }
        whenever(repository.invoke()).thenReturn(flow)
        val expected = BaseResult.Errors(Throwable())
        useCase.invoke().collectLatest {
            Assertions.assertEquals(expected.toString(), it.toString())
        }
    }
}