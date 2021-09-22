package com.tutorial.fakebot.view

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.fakebot.R
import com.tutorial.fakebot.databinding.ChatListItemBinding
import com.tutorial.fakebot.model.FbMessage

class FbAdapter(private val context: Context): ListAdapter<FbMessage, FbAdapter.ViewHolder>(
    DiffCallback
) {
    companion object DiffCallback : DiffUtil.ItemCallback<FbMessage>() {
        override fun areItemsTheSame(oldItem: FbMessage, newItem: FbMessage): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: FbMessage, newItem: FbMessage): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatListItemBinding.inflate(LayoutInflater
            .from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatMessage = getItem(position)
        holder.bind(chatMessage)
    }

    inner class ViewHolder(private val binding: ChatListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fbMessage: FbMessage) {
            val chatListItemMessage = binding.fbItem
            if (fbMessage.isQuestion) {
                chatListItemMessage.gravity = Gravity.END
                chatListItemMessage.setBackgroundColor(ContextCompat.getColor(context,
                    R.color.purple_200
                ))
            } else {
                chatListItemMessage.gravity = Gravity.START
                chatListItemMessage.setBackgroundColor(ContextCompat.getColor(context,
                    R.color.teal_200
                ))
            }

            chatListItemMessage.text = fbMessage.message
            binding.executePendingBindings()
        }
    }
}
