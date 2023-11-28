package com.example.githubapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.data.response.UserFollowerResponseItem
import com.example.githubapp.databinding.ItemUsernameBinding

class FollowerListAdapter : ListAdapter <UserFollowerResponseItem, FollowerListAdapter.FollowerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val viewBinding = ItemUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val follower = getItem(position)
        holder.bind(follower)
    }

    class FollowerViewHolder (val viewBinding: ItemUsernameBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(follower : UserFollowerResponseItem) {
            viewBinding.tvUsername.text = follower.login
            Glide.with(itemView)
                .load(follower.avatarUrl)
                .into(viewBinding.imgUser)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowerResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserFollowerResponseItem,
                newItem: UserFollowerResponseItem
            ): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: UserFollowerResponseItem,
                newItem: UserFollowerResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}