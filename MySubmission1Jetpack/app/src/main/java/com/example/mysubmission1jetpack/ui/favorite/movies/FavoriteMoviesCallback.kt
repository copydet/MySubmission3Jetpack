package com.example.mysubmission1jetpack.ui.favorite.movies

import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity

interface FavoriteMoviesCallback {
    fun onShareClick(movies: MoviesEntity)
}