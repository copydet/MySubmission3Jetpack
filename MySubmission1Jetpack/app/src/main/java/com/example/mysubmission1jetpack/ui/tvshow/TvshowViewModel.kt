package com.example.mysubmission1jetpack.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mysubmission1jetpack.data.source.FilmRepository
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity
import com.example.mysubmission1jetpack.vo.Resource


class TvshowViewModel(private val tvShowRepository: FilmRepository): ViewModel() {
    fun getTvshow(): LiveData<Resource<PagedList<TvShowEntity>>> = tvShowRepository.getAllTvShow()
}