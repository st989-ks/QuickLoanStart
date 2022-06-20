
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

class RegistrationUseCaseTest{

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var useCase: RegistrationUseCase

    @Mock
    private lateinit var repository: RegistrationRepository


    private val anyString = "any"
    private val anyInt = 0
    private val registrationEntity = RegistrationEntity(
        name = anyString,
        role= anyString
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = RegistrationUseCase(repository)
    }

    @Test
    fun `when execute usecase return success response`() = runBlocking {
        val flow = flow<BaseResult<RegistrationEntity, Throwable>> {
            emit(BaseResult.Success(registrationEntity))
        }
        whenever(repository.invoke(any())).thenReturn(flow)
        val expected = BaseResult.Success(registrationEntity)
        useCase.invoke(RegistrationRequest(anyString, anyString)).collectLatest {
            Assertions.assertEquals(expected, it)
        }
    }

    @Test
    fun `when execute usecase return error response`() = runBlocking {
        val flow = flow<BaseResult<RegistrationEntity, Throwable>> {
            emit(BaseResult.Errors(Throwable()))
        }
        whenever(repository.invoke(any())).thenReturn(flow)
        val expected = BaseResult.Errors(Throwable())
        useCase.invoke(RegistrationRequest(anyString, anyString)).collectLatest {
            Assertions.assertEquals(expected.toString(), it.toString())
        }
    }
}