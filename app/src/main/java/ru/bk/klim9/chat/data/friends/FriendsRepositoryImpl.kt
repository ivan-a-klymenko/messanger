package ru.bk.klim9.chat.data.friends

import ru.bk.klim9.chat.data.account.AccountCache
import ru.bk.klim9.chat.domain.friends.FriendEntity
import ru.bk.klim9.chat.domain.friends.FriendsRepository
import ru.bk.klim9.chat.domain.type.*

class FriendsRepositoryImpl(
    private val accountCache: AccountCache,
    private val friendsRemote: FriendsRemote,
    private val friendsCache: FriendsCache
) : FriendsRepository {

    override fun getFriends(needFetch: Boolean): Either<Failure, List<FriendEntity>> {
        return accountCache.getCurrentAccount()
            .flatMap {
                return@flatMap if (needFetch) {
                    friendsRemote.getFriends(it.id, it.token)
                } else {
                    Either.Right(friendsCache.getFriends())
                }
            }
            .map { it.sortedBy { it.name } }
            .onNext { it.map { friendsCache.saveFriend(it) } }
    }

    override fun getFriendRequests(needFetch: Boolean): Either<Failure, List<FriendEntity>> {
        return accountCache.getCurrentAccount()
            .flatMap {
                return@flatMap if (needFetch) {
                    friendsRemote.getFriendRequests(it.id, it.token)
                } else {
                    Either.Right(friendsCache.getFriendRequests())
                }
            }
            .map { it.sortedBy { it.name } }
            .onNext { it.map {
                it.isRequest = 1
                friendsCache.saveFriend(it)
            } }
    }

    override fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.approveFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
            .onNext {
                friendEntity.isRequest = 0
                friendsCache.saveFriend(friendEntity)
            }
    }

    override fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.cancelFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
            .onNext { friendsCache.removeFriendEntity(friendEntity.id) }
    }

    override fun addFriend(email: String): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.addFriend(email, it.id, it.token) }
    }

    override fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.deleteFriend(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
            .onNext { friendsCache.removeFriendEntity(friendEntity.id) }
    }
}