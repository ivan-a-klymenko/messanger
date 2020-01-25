package ru.bk.klim9.chat.domain.account

import ru.bk.klim9.chat.domain.type.None
import ru.bk.klim9.chat.domain.interactor.UseCase
import javax.inject.Inject

class UpdateToken @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<None, UpdateToken.Params>() {

    override suspend fun run(params: Params) = accountRepository.updateAccountToken(params.token)

    data class Params(val token: String)
}