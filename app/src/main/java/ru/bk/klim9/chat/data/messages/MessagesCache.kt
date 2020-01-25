package ru.bk.klim9.chat.data.messages

import ru.bk.klim9.chat.domain.messages.MessageEntity

interface MessagesCache {
    fun saveMessage(entity: MessageEntity)

    fun saveMessages(entities: List<MessageEntity>)

    fun getChats(): List<MessageEntity>

    fun getMessagesWithContact(contactId: Long): List<MessageEntity>

    fun deleteMessagesByUser(messageId: Long)
}