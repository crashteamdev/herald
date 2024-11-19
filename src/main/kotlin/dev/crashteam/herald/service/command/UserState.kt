package dev.crashteam.herald.service.command

enum class UserState {
    AWAITING_AUTH,
    ASK_LOGIN,
    ASK_PASSWORD,
    COMPLETED
}