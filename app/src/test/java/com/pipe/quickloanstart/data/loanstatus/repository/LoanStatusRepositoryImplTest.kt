
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

class LoanStatusRepositoryImplTest {

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var impl: LoanStatusRepositoryImpl

    @Mock
    private lateinit var api: LoanStatusApi


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
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        impl = LoanStatusRepositoryImpl(api)
    }

    @Test
    fun `when execute usecase return success response`() = runBlocking {
        val expected = BaseResult.Success(loanEntity)
        whenever(api.login(anyInt)).thenReturn(loanEntity)
        impl.invoke(anyInt).collectLatest {
            Assertions.assertEquals(expected, it)
        }
    }
}