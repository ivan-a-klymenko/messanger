package ru.bk.klim9.chat.remote.messages

import ru.bk.klim9.chat.domain.friends.FriendEntity
import ru.bk.klim9.chat.domain.messages.MessageEntity
import ru.bk.klim9.chat.remote.core.BaseResponse

class GetMessagesResponse (
    success: Int,
    message: String,
    val messages: List<MessageEntity>
) : BaseResponse(success, message)