import com.badikova.client.domain.User
import com.badikova.client.dto.AddMoneyRequestDto
import com.badikova.client.dto.RegisterUserRequestDto
import com.badikova.common.dto.BaseResponse
import com.badikova.common.exception.StocksIllegalRequestException
import com.badikova.common.exception.UserAlreadyRegisteredException
import com.badikova.common.exception.UserNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime

internal class UserServiceTest: AbstractTestContainersIntegrationTest() {


    @Test
    fun testRegistration() {
        val dto = RegisterUserRequestDto("NASTYA")
        val response = userController.registerUser(dto)
        Assertions.assertNotNull(response)
        assertEquals("NASTYA", response.block()?.login)
    }

    @Test
    fun testRegistrationUserAlreadyExist() {
        val dto = RegisterUserRequestDto("NASTYA")
        userController.registerUser(dto)
        val secondResponse = userController.registerUser(dto)
        Assertions.assertNotNull(secondResponse)
        Assertions.assertThrows(UserAlreadyRegisteredException::class.java) { secondResponse.block() }
    }

    @Test
    fun testAddMoneySuccess() {
        userRepository.save(User("NASTYA", BigDecimal.ZERO, LocalDateTime.now())).subscribe()
        val dto = AddMoneyRequestDto("NASTYA", BigDecimal(10))
        val response: Mono<BaseResponse> = userController.addMoneyToAccount(dto)
        Assertions.assertNotNull(response)
        assertTrue(response.block()!!.success)
        assertEquals(userRepository.findByLogin("NASTYA").block()?.balance, BigDecimal(10))
    }

    @Test
    fun testAddMoneyUserNotExist() {
        val dto = AddMoneyRequestDto("NASTYA", BigDecimal(10))
        val response: Mono<BaseResponse> = userController.addMoneyToAccount(dto)
        Assertions.assertNotNull(response)
        Assertions.assertThrows(UserNotFoundException::class.java) { response.block() }
    }

    @Test
    fun testAddMoneyUserIncorrectSum() {
        val dto = AddMoneyRequestDto("NASTYA", BigDecimal(-1))
        Assertions.assertThrows(StocksIllegalRequestException::class.java) { userController.addMoneyToAccount(dto) }
    }
}