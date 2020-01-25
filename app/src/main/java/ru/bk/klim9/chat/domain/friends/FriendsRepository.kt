package ru.bk.klim9.chat.domain.friends

import ru.bk.klim9.chat.domain.type.Either
import ru.bk.klim9.chat.domain.type.Failure
import ru.bk.klim9.chat.domain.type.None

interface FriendsRepository {
    fun getFriends(needFetch: Boolean): Either<Failure, List<FriendEntity>>
    fun getFriendRequests(needFetch: Boolean): Either<Failure, List<FriendEntity>>

    fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None>
    fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None>

    fun addFriend(email: String): Either<Failure, None>
    fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None>
}