
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

class RegistrationViewModelTest{

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var registrationUseCase: RegistrationUseCase

    @Mock
    private lateinit var registrationViewModel: RegistrationViewModel

    private val anyString = "any"
    private val anyInt = 0
    private val registrationEntity = RegistrationEntity(
        name = anyString,
        role= anyString
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        registrationViewModel = RegistrationViewModel(registrationUseCase)
    }

    @Test
    fun `init State result State Init`() = runBlocking {
        val actualInit = State.Init
        val expected = registrationViewModel.state.value
        Assertions.assertEquals(actualInit, expected)
    }

    @Test
    fun `when start return IsLoading(true)`() = runBlocking {
        val flow = flow<BaseResult<RegistrationEntity, Throwable>> { BaseResult.Success(registrationEntity) }
        whenever(registrationUseCase.invoke(any())).thenReturn(flow)
        registrationViewModel.start(anyString, anyString)
        val actual = registrationViewModel.state.value
        val expected = State.IsLoading(true)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return TokenEntity result Success(token)`() = runBlocking {
        val flow = flow<BaseResult<RegistrationEntity, Throwable>> {
            emit(BaseResult.Success(registrationEntity))
        }
        whenever(registrationUseCase.invoke(any())).thenReturn(flow)
        registrationViewModel.start(anyString, anyString)
        val actual = registrationViewModel.state.value
        val expected = State.Success(registrationEntity)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `when LoginRequest return Error`() = runBlocking {
        val flow = flow<BaseResult<RegistrationEntity, Throwable>> {
            emit(BaseResult.Errors(Throwable("Errors")))
        }
        flow.collect {
            println(it)
        }
        whenever(registrationUseCase.invoke(any())).thenReturn(flow)
        registrationViewModel.start(anyString, anyString)
        val actual = registrationViewModel.state.value.toString()
        val expected = State.Error(Throwable("Errors")).toString()
        Assertions.assertEquals(expected, actual)
    }
}
