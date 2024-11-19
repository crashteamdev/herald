package dev.crashteam.herald.service.command

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand

interface BaseCommand {

    fun getBotCommand(): BotCommand

    fun getAction(update: Update): BotApiMethod<Message>
}