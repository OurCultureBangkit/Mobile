package com.example.ourculture.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WishlistDao {
    @Query("SELECT * FROM wishlist ORDER BY id DESC")
    fun getWishlist(): LiveData<List<WishlistEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWishlist(wishlist: List<WishlistEntity>)

    @Query("DELETE FROM wishlist")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(wishlist: WishlistEntity)
}