package com.example.mysubmission1jetpack.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mysubmission1jetpack.data.source.FilmRepository
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity

class FavoriteTvShowViewModel(private val tvShowRepository: FilmRepository): ViewModel() {
    fun getTvShowFavorite(): LiveData<PagedList<TvShowEntity>>{
        return tvShowRepository.getFavoriteTvShow()
    }
    fun setFavoriteTvShow(tvShowEntity: TvShowEntity){
        val state = !tvShowEntity.favoriteTvShow
        tvShowRepository.setTvShowFavorite(tvShowEntity, state)
    }
}