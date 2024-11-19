package dev.crashteam.herald.util

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Component
class MessageSender(
    private val bot: AbsSender // Бот передается сюда
) {

    fun sendMessage(chatId: Long, text: String) {
        try {
            val message = SendMessage(chatId.toString(), text)
            bot.execute(message)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    fun deleteMessage(chatId: Long, messageId: Int) {
        try {
            val deleteMessage = DeleteMessage(chatId.toString(), messageId)
            bot.execute(deleteMessage)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

}