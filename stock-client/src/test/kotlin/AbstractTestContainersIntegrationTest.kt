import AbstractTestContainersIntegrationTest.PropertiesInitializer
import com.badikova.client.controller.UserController
import com.badikova.client.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

class MyPostgreSQLContainer(imageName: String) : PostgreSQLContainer<MyPostgreSQLContainer>(imageName)

@Container
private val postgres: MyPostgreSQLContainer = MyPostgreSQLContainer("postgres:13")
    .withDatabaseName("users")
    .withUsername("postgres")
    .withPassword("password").also { it.start() }


@SpringBootTest(classes = [UserServiceTest::class, AbstractTestContainersIntegrationTest::class])
@ContextConfiguration(initializers = [PropertiesInitializer::class])
abstract class AbstractTestContainersIntegrationTest() {

    @Autowired
    lateinit var userController: UserController

    @Autowired
    lateinit var userRepository: UserRepository

    internal class PropertiesInitializer : ApplicationContextInitializer<ConfigurableApplicationContext?> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.r2dbc.url=" + postgres.jdbcUrl.replace("jdbc", "r2dbc"),
                "spring.r2dbc.username=" + postgres.username,
                "spring.r2dbc.password=" + postgres.password
            ).applyTo(configurableApplicationContext.environment)
        }
    }

}