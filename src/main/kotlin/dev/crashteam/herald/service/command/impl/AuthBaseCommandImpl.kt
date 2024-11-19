package dev.crashteam.herald.service.command.impl

import dev.crashteam.herald.Bot
import dev.crashteam.herald.service.command.BaseCommand
import dev.crashteam.herald.service.command.UserState
import dev.crashteam.herald.service.command.UserStateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand

@Service
class AuthBaseCommandImpl @Autowired constructor(
    private val userStateRepository: UserStateRepository,
    @Lazy private val bot: Bot
) : BaseCommand {

    override fun getBotCommand(): BotCommand {
        return BotCommand.builder()
            .command("auth")
            .description("Сохранить/обновить данные")
            .build()
    }

    override fun getAction(update: Update): BotApiMethod<Message> {
        processingState(update)
        processingState(update)
        processingState(update)

        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Авторизация")
            .parseMode(ParseMode.MARKDOWN)
            .build()
    }

    fun processingState(update: Update) {
        val message = update.message
        val chatId = message.chatId
        val text = message.text
        val state: UserState = userStateRepository.getState(chatId)

        when (state) {
            UserState.AWAITING_AUTH -> {
                userStateRepository.setState(chatId, UserState.ASK_LOGIN)
                bot.execute(sendMessage(chatId, "Привет! Для начала, пожалуйста, укажите ваш login."))
            }

            UserState.ASK_LOGIN -> {
                userStateRepository.saveResponse(chatId, "login", message.text)
                userStateRepository.setState(chatId, UserState.ASK_PASSWORD)
                bot.execute(sendMessage(chatId, "Отлично! Теперь укажите ваш email:"))
            }

            UserState.ASK_PASSWORD -> {
                userStateRepository.saveResponse(chatId, "password", message.text)
                userStateRepository.setState(chatId, UserState.COMPLETED)
                bot.execute(sendMessage(chatId, "Спасибо!"))
            }

            UserState.COMPLETED -> {
                bot.execute(sendMessage(chatId, "Вы уже завершили процесс регистрации"))
            }
        }
    }

    private fun sendMessage(chatId: Long, text: String): SendMessage {
        return SendMessage.builder()
            .chatId(chatId)
            .text(text)
            .parseMode(ParseMode.MARKDOWN)
            .build()
    }
}
