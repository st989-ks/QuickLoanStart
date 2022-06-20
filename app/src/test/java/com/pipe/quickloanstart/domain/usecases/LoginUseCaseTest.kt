
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.flow.collectLatest
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

class LoginUseCaseTest {

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var useCase: LoginUseCase

    @Mock
    private lateinit var repository: LoginRepository


    private val anyString = "any"
    private val anyInt = 0
    private val tokenEntity = TokenEntity(anyString)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = LoginUseCase(repository)
    }

    @Test
    fun `when execute usecase return success response`() = runBlocking {
        val flow = flow<BaseResult<TokenEntity, Throwable>> {
            emit(BaseResult.Success(tokenEntity))
        }
        whenever(repository.invoke(any())).thenReturn(flow)
        val expected = BaseResult.Success(tokenEntity)
        useCase.invoke(LoginRequest(anyString, anyString)).collectLatest {
            assertEquals(expected, it)
        }
    }

    @Test
    fun `when execute usecase return error response`() = runBlocking {
        val flow = flow<BaseResult<TokenEntity, Throwable>> {
            emit(BaseResult.Errors(Throwable()))
        }
        whenever(repository.invoke(any())).thenReturn(flow)
        val expected = BaseResult.Errors(Throwable())
        useCase.invoke(LoginRequest(anyString, anyString)).collectLatest {
            assertEquals(expected.toString(), it.toString())
        }
    }
}