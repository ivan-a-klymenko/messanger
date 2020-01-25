package ru.bk.klim9.chat.remote.friends

import ru.bk.klim9.chat.domain.account.AccountEntity
import ru.bk.klim9.chat.domain.friends.FriendEntity
import ru.bk.klim9.chat.remote.core.BaseResponse

class GetFriendsResponse (
    success: Int,
    message: String,
    val friends: List<FriendEntity>
) : BaseResponse(success, message)