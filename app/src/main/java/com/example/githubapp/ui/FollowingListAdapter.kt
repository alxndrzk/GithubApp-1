package com.example.githubapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.data.response.UserFollowingResponseItem
import com.example.githubapp.data.response.UserFollowerResponseItem
import com.example.githubapp.databinding.ItemUsernameBinding

class FollowingListAdapter : ListAdapter<UserFollowingResponseItem, FollowingListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewBinding = ItemUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val following = getItem(position)
        holder.bind(following)
    }

    class MyViewHolder (val viewBinding: ItemUsernameBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(following : UserFollowingResponseItem) {
            viewBinding.tvUsername.text = following.login
            Glide.with(itemView)
                .load(following.avatarUrl)
                .into(viewBinding.imgUser)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowingResponseItem>(){
            override fun areItemsTheSame(
                oldItem: UserFollowingResponseItem,
                newItem: UserFollowingResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserFollowingResponseItem,
                newItem: UserFollowingResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}