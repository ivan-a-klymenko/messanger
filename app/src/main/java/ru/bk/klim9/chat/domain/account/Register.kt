package ru.bk.klim9.chat.domain.account

import ru.bk.klim9.chat.domain.type.None
import ru.bk.klim9.chat.domain.type.Either
import ru.bk.klim9.chat.domain.type.Failure
import ru.bk.klim9.chat.domain.interactor.UseCase
import javax.inject.Inject

class Register @Inject constructor(
    private val repository: AccountRepository
) : UseCase<None, Register.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return repository.register(params.email, params.name, params.password)
    }

    data class Params(val email: String, val name: String, val password: String)
}

