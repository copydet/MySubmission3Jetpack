package com.example.mysubmission1jetpack.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mysubmission1jetpack.data.source.FilmRepository
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.vo.Resource


class MovieViewModel(private val movieRepository: FilmRepository): ViewModel() {
    fun getMovie(): LiveData<Resource<PagedList<MoviesEntity>>> = movieRepository.getAllMovie()
}