package dev.crashteam.herald.model

data class CheckTokenResponse(
    val accountId: Long,
    val active: Boolean,
    val firstName: String,
    val email: String,
    val sellerId: Long
)
