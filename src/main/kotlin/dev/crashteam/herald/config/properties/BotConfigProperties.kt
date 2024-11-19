package dev.crashteam.herald.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "bot")
class BotConfigProperties {

    var token: String? = null

    var username: String? = null

}
