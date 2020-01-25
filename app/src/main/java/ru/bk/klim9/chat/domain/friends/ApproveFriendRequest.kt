package ru.bk.klim9.chat.domain.friends

import ru.bk.klim9.chat.domain.interactor.UseCase
import ru.bk.klim9.chat.domain.type.None
import javax.inject.Inject

class ApproveFriendRequest @Inject constructor(
    private val friendsRepository: FriendsRepository
) : UseCase<None, FriendEntity>() {

    override suspend fun run(params: FriendEntity) = friendsRepository.approveFriendRequest(params)
}