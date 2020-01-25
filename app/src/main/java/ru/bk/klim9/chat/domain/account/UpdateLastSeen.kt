package ru.bk.klim9.chat.domain.account

import ru.bk.klim9.chat.domain.interactor.UseCase
import ru.bk.klim9.chat.domain.type.None
import javax.inject.Inject

class UpdateLastSeen @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<None, None>() {

    override suspend fun run(params: None) = accountRepository.updateAccountLastSeen()
}