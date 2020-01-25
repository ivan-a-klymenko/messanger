package ru.bk.klim9.chat.domain.messages

import ru.bk.klim9.chat.domain.interactor.UseCase
import ru.bk.klim9.chat.domain.type.None
import javax.inject.Inject

class GetChats @Inject constructor(
    private val messagesRepository: MessagesRepository
) : UseCase<List<MessageEntity>, GetChats.Params>() {

    override suspend fun run(params: Params) = messagesRepository.getChats(params.needFetch)

    data class Params(val needFetch: Boolean)
}