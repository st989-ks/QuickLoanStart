
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

class TermsRepositoryImplTest {
    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var impl: TermsRepositoryImpl

    @Mock
    private lateinit var api: TermsApi


    private val anyString = "any"
    private val anyInt = 11
    private val termsEntity = TermsEntity(
        maxAmount = anyInt,
        percent = anyInt.toDouble(),
        period = anyInt,
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        impl = TermsRepositoryImpl(api)
    }

    @Test
    fun `when execute usecase return success response`() = runBlocking {
        val expected = BaseResult.Success(termsEntity)
        whenever(api.response()).thenReturn(termsEntity)
        impl.invoke().collectLatest {
            Assertions.assertEquals(expected, it)
        }
    }
}