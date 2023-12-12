package com.example.ourculture.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ourculture.data.remote.retrofit.response.BarangItem
import com.example.ourculture.data.remote.retrofit.response.DataItem
import com.example.ourculture.databinding.ItemCultureBinding
import com.example.ourculture.databinding.ItemMarketplaceBinding
import com.example.ourculture.ui.detail.DetailCultureActivity
import com.example.ourculture.ui.detailmarketplace.DetailMarketplaceActivity

class HomeAdapter: PagingDataAdapter<DataItem, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemCultureBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cultureItem: DataItem){
            Glide.with(binding.root.context)
                .load(cultureItem.image)
                .into(binding.imgPoster)
            binding.tvTitle.text = cultureItem.name
            binding.tvDescription.text = cultureItem.description
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailCultureActivity::class.java)
                intent.putExtra(DetailCultureActivity.EXTRA_ID, cultureItem.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCultureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val culture = getItem(position)
        if (culture != null) {
            holder.bind(culture)
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}