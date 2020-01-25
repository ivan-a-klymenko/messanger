package ru.bk.klim9.chat.data.account

import ru.bk.klim9.chat.domain.account.AccountEntity
import ru.bk.klim9.chat.domain.type.Either
import ru.bk.klim9.chat.domain.type.None
import ru.bk.klim9.chat.domain.type.Failure

interface AccountCache {
    fun getToken(): Either<Failure, String>
    fun saveToken(token: String): Either<Failure, None>

    fun logout(): Either<Failure, None>

    fun getCurrentAccount(): Either<Failure, AccountEntity>
    fun saveAccount(account: AccountEntity): Either<Failure, None>

    fun checkAuth(): Either<Failure, Boolean>
}