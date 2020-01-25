package ru.bk.klim9.chat.data.account

import ru.bk.klim9.chat.domain.account.AccountEntity
import ru.bk.klim9.chat.domain.type.Either
import ru.bk.klim9.chat.domain.type.None
import ru.bk.klim9.chat.domain.type.Failure

interface AccountRemote {
    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None>

    fun login(email: String, password: String, token: String): Either<Failure, AccountEntity>

    fun updateToken(userId: Long, token: String, oldToken: String): Either<Failure, None>

    fun editUser(
        userId: Long,
        email: String,
        name: String,
        password: String,
        status: String,
        token: String,
        image: String
    ): Either<Failure, AccountEntity>

    fun updateAccountLastSeen(userId: Long, token: String, lastSeen: Long): Either<Failure, None>
}