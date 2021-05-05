package com.example.mysubmission1jetpack.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity
import com.example.mysubmission1jetpack.vo.Resource

interface FilmDataSource {
    fun getAllMovie(): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getFavoriteMovie(): LiveData<PagedList<MoviesEntity>>

    fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>>

    fun getDetailMovie(movieId: String): LiveData<Resource<MoviesEntity>>

    fun getDetailTvshow(tvshowId: String): LiveData<Resource<TvShowEntity>>

    fun setMovieFavorite(movie: MoviesEntity, state: Boolean)

    fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean)
}