package com.example.ourculture.ui.marketplace
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ourculture.data.remote.retrofit.response.BarangItem
import com.example.ourculture.databinding.ItemMarketplaceBinding
import com.example.ourculture.ui.detail.DetailCultureActivity
import com.example.ourculture.ui.detailmarketplace.DetailMarketplaceActivity

class MarketplaceAdapter: PagingDataAdapter<BarangItem, MarketplaceAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemMarketplaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(barangItem: BarangItem){
            Glide.with(binding.root.context)
                .load(barangItem.images)
                .into(binding.imgPoster)
            binding.tvTitle.text = barangItem.title
            binding.tvPrice.text = "Rp.${barangItem.harga}"
            binding.tvLocation.text = barangItem.location
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMarketplaceActivity::class.java)
                intent.putExtra(DetailMarketplaceActivity.EXTRA_ID, barangItem.id.toString())
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMarketplaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BarangItem>() {
            override fun areItemsTheSame(oldItem: BarangItem, newItem: BarangItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BarangItem, newItem: BarangItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}