
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class LoginRepositoryImplTest{

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var impl: LoginRepositoryImpl

    @Mock
    private lateinit var api: LoginApi


    private val anyString = "any"
    private val anyInt = 0
    private val tokenEntity = TokenEntity(anyString)
    private val loginRequest = LoginRequest(anyString,anyString)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        impl = LoginRepositoryImpl(api)
    }

    @org.junit.Test
    fun `when execute usecase return success response`() = runBlocking {
        val expected = BaseResult.Success(tokenEntity)
        whenever(api.login(loginRequest)).thenReturn(tokenEntity.token)
        impl.invoke(loginRequest).collectLatest {
            Assertions.assertEquals(expected.data.token, (it as BaseResult.Success<TokenEntity>).data.token)
        }
    }
}