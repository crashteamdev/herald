package dev.crashteam.herald.service.command.impl

import dev.crashteam.herald.service.command.BaseCommand
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand

@Service
class StartBaseCommandImpl : BaseCommand {

    override fun getBotCommand(): BotCommand {
        return BotCommand.builder()
            .command("start")
            .description("Запустить/восстановить работу с ботом")
            .build()
    }

    override fun getAction(update: Update): BotApiMethod<Message> {
        return SendMessage.builder()
            .chatId(update.message.chatId)
            .text("Вас приветствует бот MarketDB")
            .parseMode(ParseMode.MARKDOWN)
            .build()
    }

}