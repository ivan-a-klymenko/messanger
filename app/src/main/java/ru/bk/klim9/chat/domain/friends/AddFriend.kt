package ru.bk.klim9.chat.domain.friends

import ru.bk.klim9.chat.domain.interactor.UseCase
import ru.bk.klim9.chat.domain.type.None
import javax.inject.Inject

class AddFriend @Inject constructor(
    private val friendsRepository: FriendsRepository
) : UseCase<None, AddFriend.Params>() {

    override suspend fun run(params: Params) = friendsRepository.addFriend(params.email)

    data class Params(val email: String)
}