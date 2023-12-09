package com.example.ourculture.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
class WishlistEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "barangId")
    val barangId: Int,

    @field:ColumnInfo(name = "itemName")
    val itemName: String,

    @field:ColumnInfo(name = "price")
    val price: String,

    @field:ColumnInfo(name = "location")
    val location: String,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String,

)