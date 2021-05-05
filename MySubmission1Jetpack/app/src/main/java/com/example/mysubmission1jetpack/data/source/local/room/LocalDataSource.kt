package com.example.mysubmission1jetpack.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity

class LocalDataSource private constructor(private val mMoviesDao: MoviesDao, private val mTvShowDao: TvShowDao){
    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(moviesDao: MoviesDao, tvShowDao: TvShowDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(moviesDao, tvShowDao)
    }

    //Movies

    fun getAllMovies(): DataSource.Factory<Int, MoviesEntity> = mMoviesDao.getMovies()

    fun getMovieFavorite(): DataSource.Factory<Int, MoviesEntity> = mMoviesDao.getFavoriteMovie()

    fun getDetailMovie(movieId : String): LiveData<MoviesEntity> = mMoviesDao.getDetailMovie(movieId)

    fun insertMovies(movies: List<MoviesEntity>) = mMoviesDao.insertMovies(movies)

    fun setMovieFavorite(movie: MoviesEntity, newState: Boolean){
        movie.favoriteMovie = newState
        mMoviesDao.updateMovies(movie)
    }

    //TV Show

    fun getAllTvShow(): DataSource.Factory<Int, TvShowEntity> = mTvShowDao.getTvShow()

    fun getTvShowFavorite(): DataSource.Factory<Int, TvShowEntity> = mTvShowDao.getFavoriteTvShow()

    fun getDetailTvShow(tvShowId : String): LiveData<TvShowEntity> = mTvShowDao.getDetailTvShow(tvShowId)

    fun insertTvShow(tvshow: List<TvShowEntity>) = mTvShowDao.insertTvShow(tvshow)

    fun setTvShowFavorite(tvshow: TvShowEntity, newState: Boolean){
        tvshow.favoriteTvShow = newState
        mTvShowDao.updateTvShow(tvshow)
    }
}