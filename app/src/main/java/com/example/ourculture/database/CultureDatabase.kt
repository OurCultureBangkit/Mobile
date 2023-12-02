package com.example.ourculture.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ourculture.data.remote.retrofit.response.ListStoryItem

@Database(
    entities = [ListStoryItem::class],
    version = 1,
    exportSchema = false
)

abstract class CultureDatabase: RoomDatabase() {

    companion object{
        @Volatile
        private var INSTANCE: CultureDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CultureDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CultureDatabase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}