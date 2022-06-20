
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

class TermsUseCaseTest {

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var useCase: TermsUseCase

    @Mock
    private lateinit var repository: TermsRepository


    private val anyString = "any"
    private val anyInt = 0
    private val termsEntity = TermsEntity(
        maxAmount = anyInt,
        percent = anyInt.toDouble(),
        period = anyInt,
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = TermsUseCase(repository)
    }

    @Test
    fun `when execute usecase return success response`() = runBlocking {
        val flow = flow<BaseResult<TermsEntity, Throwable>> {
            emit(BaseResult.Success(termsEntity))
        }
        whenever(repository.invoke()).thenReturn(flow)
        val expected = BaseResult.Success(termsEntity)
        useCase.invoke().collectLatest {
            Assertions.assertEquals(expected, it)
        }
    }

    @Test
    fun `when execute usecase return error response`() = runBlocking {
        val flow = flow<BaseResult<TermsEntity, Throwable>> {
            emit(BaseResult.Errors(Throwable()))
        }
        whenever(repository.invoke()).thenReturn(flow)
        val expected = BaseResult.Errors(Throwable())
        useCase.invoke().collectLatest {
            Assertions.assertEquals(expected.toString(), it.toString())
        }
    }
}