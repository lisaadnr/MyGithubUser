package com.dicoding.mygithubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.mygithubuser.data.response.GithubUser
import com.dicoding.mygithubuser.databinding.ItemUserBinding

class UserAdapter(private val onItemClickCallback: OnItemClickCallback) : ListAdapter<GithubUser, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClickCallback)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }
    class MyViewHolder(private val binding: ItemUserBinding, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: GithubUser){
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(review)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(review.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivUser)
                tvUsername.text = review.login
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUser
                >() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
        }

    }


    interface OnItemClickCallback{
        fun onItemClicked(data: GithubUser)
    }


}