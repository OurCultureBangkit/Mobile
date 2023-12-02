package com.example.ourculture.ui.marketplace
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ourculture.data.remote.retrofit.response.ListStoryItem
import com.example.ourculture.databinding.ItemMarketplaceBinding
import com.example.ourculture.ui.detail.DetailCultureActivity
import com.example.ourculture.ui.detailmarketplace.DetailMarketplaceActivity

class MarketplaceAdapter: PagingDataAdapter<ListStoryItem, MarketplaceAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemMarketplaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(storyItem: ListStoryItem){
            Glide.with(binding.root.context)
                .load(storyItem.photoUrl)
                .into(binding.imgPoster)
            binding.tvTitle.text = storyItem.name
            binding.tvPrice.text = "Rp10.000"
            //binding.tvLocation.text = "Kota Bali"
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMarketplaceActivity::class.java)
                //intent.putExtra(DetailMarketplaceActivity.EXTRA_ID, storyItem.id)
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
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ListStoryItem> =
            object : DiffUtil.ItemCallback<ListStoryItem>() {
                override fun areItemsTheSame(
                    oldItem: ListStoryItem,
                    newItem: ListStoryItem
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ListStoryItem,
                    newItem: ListStoryItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

}