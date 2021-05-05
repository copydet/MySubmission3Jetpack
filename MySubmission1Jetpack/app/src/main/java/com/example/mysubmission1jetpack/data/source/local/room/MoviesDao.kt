package com.example.mysubmission1jetpack.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity


@Dao
interface MoviesDao {
    //MovieDao
    @Query("SELECT * FROM movieentities")
    fun getMovies(): DataSource.Factory<Int, MoviesEntity>

    @Query("SELECT * FROM movieentities where favoriteMovie = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MoviesEntity>

    //Transaction
    @Transaction
    @Query("SELECT * FROM movieentities WHERE movieId = :movieId")
    fun getDetailMovie(movieId: String): LiveData<MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MoviesEntity>)

    @Update
    fun updateMovies(movies: MoviesEntity)

}