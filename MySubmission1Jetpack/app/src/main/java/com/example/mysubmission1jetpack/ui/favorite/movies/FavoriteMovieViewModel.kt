package com.example.mysubmission1jetpack.ui.favorite.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mysubmission1jetpack.data.source.FilmRepository
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity

class FavoriteMovieViewModel(private val movieRepository: FilmRepository): ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<MoviesEntity>>{
        return movieRepository.getFavoriteMovie()
    }

    fun setFavoriteMovie(moviesEntity: MoviesEntity){
        val state = !moviesEntity.favoriteMovie
        movieRepository.setMovieFavorite(moviesEntity, state)
    }
}