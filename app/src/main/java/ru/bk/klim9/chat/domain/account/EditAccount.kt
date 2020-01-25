package ru.bk.klim9.chat.domain.account

import ru.bk.klim9.chat.domain.interactor.UseCase
import ru.bk.klim9.chat.domain.type.Either
import ru.bk.klim9.chat.domain.type.Failure
import javax.inject.Inject

class EditAccount @Inject constructor(
    private val repository: AccountRepository
) : UseCase<AccountEntity, AccountEntity>() {

    override suspend fun run(params: AccountEntity): Either<Failure, AccountEntity> {
        return repository.editAccount(params)
    }
}