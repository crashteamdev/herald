package dev.crashteam.herald.service.command

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class UserStateRepository {

    private val userStates: MutableMap<Long, UserState> = ConcurrentHashMap()
    private val userResponses: MutableMap<Long, MutableMap<String, String>> = ConcurrentHashMap()

    fun getState(chatId: Long): UserState = userStates[chatId] ?: UserState.AWAITING_AUTH

    fun setState(chatId: Long, userState: UserState) {
        userStates[chatId] = userState
    }

    fun saveResponse(chatId: Long, key: String, value: String) {
        userResponses.computeIfAbsent(chatId) { mutableMapOf() }[key] = value
    }

    fun getResponse(chatId: Long): Map<String, String>? = userResponses[chatId]

}