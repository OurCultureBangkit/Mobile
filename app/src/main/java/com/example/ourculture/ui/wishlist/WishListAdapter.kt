package com.example.ourculture.ui.wishlist

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ourculture.data.remote.retrofit.response.BarangItemWishList
import com.example.ourculture.database.WishlistEntity
import com.example.ourculture.databinding.ItemWishlistBinding
import com.example.ourculture.ui.detailmarketplace.DetailMarketplaceActivity

class WishListAdapter(private val onTrashcanClick: (WishlistEntity) -> Unit): ListAdapter<WishlistEntity, WishListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemWishlistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(barangItemWishList: WishlistEntity){
            Glide.with(binding.root.context)
                .load(barangItemWishList.urlToImage)
                .into(binding.ivImgWishlistItem)
            binding.tvItemTitle.text = barangItemWishList.itemName
            binding.tvWishlistPrice.text = "Rp.${barangItemWishList.price}"
            binding.tvItemLocation.text = barangItemWishList.location
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMarketplaceActivity::class.java)
                intent.putExtra(DetailMarketplaceActivity.EXTRA_ID, barangItemWishList.barangId.toString())
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val wishList = getItem(position)
        if (wishList != null) {
            holder.bind(wishList)
        }
        holder.binding.ibRemoveWishlistItem.setOnClickListener {
            onTrashcanClick(wishList)
        }
    }

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<WishlistEntity> =
            object : DiffUtil.ItemCallback<WishlistEntity>() {
                override fun areItemsTheSame(
                    oldItem: WishlistEntity,
                    newItem: WishlistEntity
                ): Boolean {
                    return oldItem == newItem
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: WishlistEntity,
                    newItem: WishlistEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}


// class WishListAdapter(private val onTrashcanClick: (BarangItemWishList) -> Unit): ListAdapter<BarangItemWishList, WishListAdapter.MyViewHolder>(DIFF_CALLBACK) {
//    class MyViewHolder(val binding: ItemWishlistBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(barangItemWishList: BarangItemWishList){
//            Glide.with(binding.root.context)
//                .load(barangItemWishList.image)
//                .into(binding.ivImgWishlistItem)
//            binding.tvItemTitle.text = barangItemWishList.title
//            binding.tvWishlistPrice.text = "Rp.${barangItemWishList.harga}"
//            binding.tvItemLocation.text = barangItemWishList.location
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, DetailMarketplaceActivity::class.java)
//                intent.putExtra(DetailMarketplaceActivity.EXTRA_ID, barangItemWishList.barangId.toString())
//                itemView.context.startActivity(intent)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val wishList = getItem(position)
//        if (wishList != null) {
//            holder.bind(wishList)
//        }
//        holder.binding.ibRemoveWishlistItem.setOnClickListener {
//            onTrashcanClick(wishList)
//        }
//    }
//
//    companion object{
//        val DIFF_CALLBACK: DiffUtil.ItemCallback<BarangItemWishList> =
//            object : DiffUtil.ItemCallback<BarangItemWishList>() {
//                override fun areItemsTheSame(
//                    oldItem: BarangItemWishList,
//                    newItem: BarangItemWishList
//                ): Boolean {
//                    return oldItem == newItem
//                }
//
//                override fun areContentsTheSame(
//                    oldItem: BarangItemWishList,
//                    newItem: BarangItemWishList
//                ): Boolean {
//                    return oldItem == newItem
//                }
//
//            }
//    }
//}