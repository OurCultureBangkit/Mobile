package com.example.ourculture.ui.detailmarketplace

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ourculture.data.remote.retrofit.response.CommmentsItem
import com.example.ourculture.databinding.ItemCommentBinding

class CommentAdapter: ListAdapter<CommmentsItem, CommentAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(commentItem: CommmentsItem){
            if (commentItem.postBy.avatar != null) {
                Glide.with(binding.root.context)
                    .load(commentItem.postBy.avatar)
                    .into(binding.profileImageComment)
            }
            binding.tvUserNameComment.text = commentItem.postBy.username
            binding.tvComment.text = commentItem.comment

            Log.d("ini link photo", commentItem.images.toString())
            Log.d("ini link photo replies", commentItem.replies[0].images.toString() )
            if (commentItem.replies[0].postBy.avatar != null) {
                Glide.with(binding.root.context)
                    .load(commentItem.replies[0].postBy.avatar)
                    .into(binding.profileImageReply)
            }
            binding.tvUserNameCommentReply.text = commentItem.replies[0].postBy.username
            binding.tvCommentReply.text = commentItem.replies[0].comment

//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, DetailMarketplaceActivity::class.java)
//                intent.putExtra(DetailMarketplaceActivity.EXTRA_ID, barangItemWishList.barangId.toString())
//                itemView.context.startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val wishList = getItem(position)
        if (wishList != null) {
            holder.bind(wishList)
        }
//        holder.binding.ibRemoveWishlistItem.setOnClickListener {
//            onTrashcanClick(wishList)
//        }
    }

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<CommmentsItem> =
            object : DiffUtil.ItemCallback<CommmentsItem>() {
                override fun areItemsTheSame(
                    oldItem: CommmentsItem,
                    newItem: CommmentsItem
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: CommmentsItem,
                    newItem: CommmentsItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}