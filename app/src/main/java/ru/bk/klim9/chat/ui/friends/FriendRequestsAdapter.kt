package ru.bk.klim9.chat.ui.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.bk.klim9.chat.databinding.ItemFriendRequestBinding
import ru.bk.klim9.chat.domain.friends.FriendEntity
import ru.bk.klim9.chat.ui.core.BaseAdapter

open class FriendRequestsAdapter :
    BaseAdapter<FriendEntity, FriendRequestsAdapter.FriendRequestViewHolder>(FriendsAdapter.FriendsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendRequestBinding.inflate(layoutInflater)
        return FriendRequestViewHolder(binding)
    }

    class FriendRequestViewHolder(val binding: ItemFriendRequestBinding) : BaseViewHolder(binding.root) {

        init {
            binding.btnApprove.setOnClickListener {
                onClick?.onClick(item, it)
            }
            binding.btnCancel.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let {
                binding.friend = it
            }
        }
    }
}