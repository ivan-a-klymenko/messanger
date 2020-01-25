package ru.bk.klim9.chat.domain.account

import ru.bk.klim9.chat.domain.interactor.UseCase
import ru.bk.klim9.chat.domain.type.Either
import ru.bk.klim9.chat.domain.type.Failure
import ru.bk.klim9.chat.domain.type.None
import javax.inject.Inject

class CheckAuth @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<Boolean, None>() {

    override suspend fun run(params: None): Either<Failure, Boolean> = accountRepository.checkAuth()
}