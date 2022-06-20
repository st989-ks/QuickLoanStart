
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class NewLoanRepositoryImplTest{

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var impl: NewLoanRepositoryImpl

    @Mock
    private lateinit var api: NewLoanApi


    private val anyString = "any"
    private val anyInt = 11
    private val loanRequest = NewLoanRequest(
        amount = anyInt,
        firstName = anyString,
        lastName = anyString,
        percent = anyInt.toDouble(),
        period = anyInt,
        phoneNumber = anyString,
    )
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
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        impl = NewLoanRepositoryImpl(api)
    }

    @Test
    fun `when execute usecase return success response`() = runBlocking {
        val expected = BaseResult.Success(loanEntity)
        whenever(api.login(loanRequest)).thenReturn(loanEntity)
        impl.invoke(loanRequest).collectLatest {
            Assertions.assertEquals(expected, it)
        }
    }
}