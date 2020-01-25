package ru.bk.klim9.chat.domain.messages

import ru.bk.klim9.chat.domain.interactor.UseCase
import ru.bk.klim9.chat.domain.type.None
import javax.inject.Inject

class DeleteMessage @Inject constructor(
    private val messagesRepository: MessagesRepository
) : UseCase<None, DeleteMessage.Params>() {

    override suspend fun run(params: Params) = messagesRepository.deleteMessagesByUser(params.messageId)

    data class Params(val messageId: Long)
}