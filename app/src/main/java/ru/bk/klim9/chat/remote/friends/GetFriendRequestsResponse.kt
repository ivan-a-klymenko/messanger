package ru.bk.klim9.chat.remote.friends

import com.google.gson.annotations.SerializedName
import ru.bk.klim9.chat.domain.friends.FriendEntity
import ru.bk.klim9.chat.remote.core.BaseResponse

class GetFriendRequestsResponse (
    success: Int,
    message: String,
    @SerializedName("friend_requests")
    val friendsRequests: List<FriendEntity>
) : BaseResponse(success, message)