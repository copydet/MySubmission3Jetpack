package com.example.mysubmission1jetpack.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity


@Dao
interface TvShowDao {

    //TvShowDao
    @Query("SELECT * FROM tvshowentities")
    fun getTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tvshowentities where favoriteTvShow = 1")
    fun  getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Transaction
    @Query("SELECT * FROM tvshowentities WHERE tvshowId = :tvshowId")
    fun getDetailTvShow(tvshowId: String): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvshow: List<TvShowEntity>)

    @Update
    fun updateTvShow(tvshow: TvShowEntity)
}