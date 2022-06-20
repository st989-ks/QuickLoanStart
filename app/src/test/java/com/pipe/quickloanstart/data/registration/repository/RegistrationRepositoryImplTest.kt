
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

class RegistrationRepositoryImplTest{
    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var impl: RegistrationRepositoryImpl

    @Mock
    private lateinit var api: RegistrationApi


    private val anyString = "any"
    private val anyInt = 11
    private val registrationEntity = RegistrationEntity(
        name = anyString,
        role= anyString
    )
    private val registrationRequest = RegistrationRequest(
        name = anyString,
        password = anyString
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        impl = RegistrationRepositoryImpl(api)
    }

    @Test
    fun `when execute usecase return success response`() = runBlocking {
        val expected = BaseResult.Success(registrationEntity)
        whenever(api.registration(registrationRequest)).thenReturn(registrationEntity)
        impl.invoke(registrationRequest).collectLatest {
            Assertions.assertEquals(expected.data.role, (it as BaseResult.Success<RegistrationEntity>).data.role)
        }
    }
}