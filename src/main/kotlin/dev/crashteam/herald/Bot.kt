package dev.crashteam.herald

import dev.crashteam.herald.config.properties.BotConfigProperties
import dev.crashteam.herald.service.command.BaseCommand
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault
import javax.annotation.PostConstruct

@Slf4j
@Component
class Bot @Autowired constructor(
    private val botConfigProperties: BotConfigProperties,
    private val baseCommands: List<BaseCommand>
) : TelegramLongPollingBot(botConfigProperties.token) {

    private val log: Logger = LoggerFactory.getLogger(Bot::class.java)

    @PostConstruct
    fun initCommand() {
        val botCommands = baseCommands.map { it.getBotCommand() }

        val myCommands = SetMyCommands.builder()
            .scope(BotCommandScopeDefault.builder().build())
            .commands(botCommands)
            .build()

        execute(myCommands)
    }

    override fun onUpdateReceived(update: Update?) {
        if (update!!.hasMessage()) {
            val message: Message = update.message
            val chatId: Long = message.chatId

            if (message.hasText()) {
                log.info("Пользователь с chatId [$chatId] отправил = " + message.text)
            }

            if (message.isCommand) {
                val baseCommand: BaseCommand = baseCommands.stream()
                    .filter { it: BaseCommand ->
                        message.text == "/" + it.getBotCommand().command
                    }
                    .findFirst()
                    .orElseThrow {
                        IllegalArgumentException("Данная команда не существует")
                    }

                execute(baseCommand.getAction(update))
            }
        }
    }

    override fun getBotUsername(): String? {
        return botConfigProperties.username
    }

}