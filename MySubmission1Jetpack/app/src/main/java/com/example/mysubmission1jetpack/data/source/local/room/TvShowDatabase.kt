package com.example.mysubmission1jetpack.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity


@Database(entities = [TvShowEntity::class],
        version = 1,
    exportSchema = false)
abstract class TvShowDatabase: RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao

    companion object{

        @Volatile
        private var INSTANCE: TvShowDatabase? = null

        fun getInstance(context: Context): TvShowDatabase =
            INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    TvShowDatabase::class.java,
                    "TvShow.db"
                ).build().apply { INSTANCE = this }
            }
    }
}