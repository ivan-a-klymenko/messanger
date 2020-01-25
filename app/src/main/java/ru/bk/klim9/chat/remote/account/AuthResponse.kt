package ru.bk.klim9.chat.remote.account

import ru.bk.klim9.chat.domain.account.AccountEntity
import ru.bk.klim9.chat.remote.core.BaseResponse

class AuthResponse(
    success: Int,
    message: String,
    val user: AccountEntity
) : BaseResponse(success, message)