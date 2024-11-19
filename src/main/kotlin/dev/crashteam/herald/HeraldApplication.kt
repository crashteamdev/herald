package dev.crashteam.herald

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan("dev.crashteam.herald.config.properties")
class HeraldApplication

fun main(args: Array<String>) {
    runApplication<HeraldApplication>(*args)
}
