package com.example.ourculture.ui.detailmarketplace

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ourculture.R
import com.example.ourculture.data.remote.retrofit.response.CommmentsItem
import com.example.ourculture.databinding.ItemCommentBinding

class CommentAdapter(private val context: Context, private val onIbReplyCommentClick: (String, String) -> Unit): ListAdapter<CommmentsItem, CommentAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, commentItem: CommmentsItem){
            binding.tvReply.setOnClickListener {
                binding.tiReplyComment.visibility = View.VISIBLE
                binding.ibSendReplyComment.visibility = View.VISIBLE
                binding.etReplyComment.requestFocus()
                val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.etReplyComment, InputMethodManager.SHOW_IMPLICIT)
            }


            if (commentItem.postBy.avatar != null) {
                Glide.with(binding.root.context)
                    .load(commentItem.postBy.avatar)
                    .into(binding.profileImageComment)
            } else {
                binding.profileImageComment.setImageResource(R.drawable.baseline_account_circle_24)
            }
            binding.tvUserNameComment.text = commentItem.postBy.username
            binding.tvComment.text = commentItem.comment

            if (commentItem.replies.isNotEmpty()) {
                binding.tvReply.visibility = View.GONE
                binding.cvReplyComment.visibility = View.VISIBLE
                if (commentItem.replies[0].postBy.avatar != null) {
                    Glide.with(binding.root.context)
                        .load(commentItem.replies[0].postBy.avatar)
                        .into(binding.profileImageReply)
                } else {
                    binding.profileImageReply.setImageResource(R.drawable.baseline_account_circle_24)
                }
                binding.tvUserNameCommentReply.text = commentItem.replies[0].postBy.username
                binding.tvCommentReply.text = commentItem.replies[0].comment
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val wishList = getItem(position)
        if (wishList != null) {
            holder.bind(context, wishList)
        }
        holder.binding.ibSendReplyComment.setOnClickListener {
            onIbReplyCommentClick(holder.binding.etReplyComment.text.toString(), wishList.id.toString())
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            holder.binding.etReplyComment.visibility = View.GONE
            it.visibility = View.GONE
        }

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