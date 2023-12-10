package com.example.ourculture.ui.setting

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ourculture.data.remote.retrofit.response.BarangItem
import com.example.ourculture.data.remote.retrofit.response.MyBarangItem
import com.example.ourculture.databinding.ItemMarketplaceBinding
import com.example.ourculture.ui.detailmarketplace.DetailMarketplaceActivity

class ProfileAdapter: PagingDataAdapter<MyBarangItem, ProfileAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemMarketplaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(barangItem: MyBarangItem){
            Glide.with(binding.root.context)
                .load(barangItem.images)
                .into(binding.imgPoster)
            binding.tvTitle.text = barangItem.title
            binding.tvPrice.text = "Rp.${barangItem.harga}"
            binding.tvLocation.text = barangItem.location
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMarketplaceActivity::class.java)
                intent.putExtra(DetailMarketplaceActivity.EXTRA_ID, barangItem.barangId.toString())
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMarketplaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val barang = getItem(position)
        if (barang != null) {
            holder.bind(barang)
        }
    }

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<MyBarangItem> =
            object : DiffUtil.ItemCallback<MyBarangItem>() {
                override fun areItemsTheSame(oldItem: MyBarangItem, newItem: MyBarangItem): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: MyBarangItem, newItem: MyBarangItem): Boolean {
                    return oldItem.barangId == newItem.barangId
                }

            }
    }
}