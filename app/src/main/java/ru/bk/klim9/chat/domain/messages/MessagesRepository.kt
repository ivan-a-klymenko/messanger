package ru.bk.klim9.chat.domain.messages

import ru.bk.klim9.chat.domain.type.Either
import ru.bk.klim9.chat.domain.type.Failure
import ru.bk.klim9.chat.domain.type.None

interface MessagesRepository {
    fun sendMessage(
        toId: Long,
        message: String,
        image: String
    ): Either<Failure, None>

    fun getChats(needFetch: Boolean): Either<Failure, List<MessageEntity>>

    fun getMessagesWithContact(contactId: Long, needFetch: Boolean): Either<Failure, List<MessageEntity>>

    fun deleteMessagesByUser(messageId: Long): Either<Failure, None>
}