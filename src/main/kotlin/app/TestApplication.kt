package app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import services.MainController

@SpringBootApplication
class TestApplication

@EnableAutoConfiguration
@Configuration
internal class Application {
    @Bean
    fun controller() = MainController()
}

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}
