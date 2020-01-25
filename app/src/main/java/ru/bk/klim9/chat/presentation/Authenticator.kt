package ru.bk.klim9.chat.presentation

import ru.bk.klim9.chat.domain.account.CheckAuth
import ru.bk.klim9.chat.domain.type.None
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator
@Inject constructor(
    val checkAuth: CheckAuth
) {
    fun userLoggedIn(body: (Boolean) -> Unit) {
        checkAuth(None()) {
            it.either({ body(false) }, { body(it) })
        }
    }
}