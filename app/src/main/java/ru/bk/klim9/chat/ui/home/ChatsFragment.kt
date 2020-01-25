package ru.bk.klim9.chat.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import ru.bk.klim9.chat.R
import ru.bk.klim9.chat.cache.ChatDatabase
import ru.bk.klim9.chat.domain.messages.MessageEntity
import ru.bk.klim9.chat.presentation.viewmodel.MessagesViewModel
import ru.bk.klim9.chat.ui.App
import ru.bk.klim9.chat.ui.core.BaseListFragment
import ru.bk.klim9.chat.ui.core.ext.onFailure
import ru.bk.klim9.chat.ui.core.ext.onSuccess


class ChatsFragment : BaseListFragment() {

    override val viewAdapter = ChatsAdapter()

    override val titleToolbar = R.string.chats

    private lateinit var messagesViewModel: MessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messagesViewModel = viewModel {
            onSuccess(progressData, ::updateProgress)
            onFailure(failureData, ::handleFailure)
        }

        viewAdapter.setOnClick( { it, v ->
            (it as? MessageEntity)?.let {
                val contact = it.contact
                if (contact != null) {
                    navigator.showChatWithContact(contact.id, contact.name, requireActivity())
                }
            }
        })

        ChatDatabase.getInstance(requireContext()).messagesDao.getLiveChats().observe(this, Observer {
            val list = it.distinctBy { it.contact?.id }.toList()
            handleChats(list)
        })
    }

    override fun onResume() {
        super.onResume()

        messagesViewModel.getChats()
    }

    fun handleChats(messages: List<MessageEntity>?) {
        if (messages != null && messages.isNotEmpty()) {
            viewAdapter.submitList(messages)
        }
    }
}
