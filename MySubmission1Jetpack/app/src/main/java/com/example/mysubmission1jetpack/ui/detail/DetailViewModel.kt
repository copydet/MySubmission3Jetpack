package com.example.mysubmission1jetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mysubmission1jetpack.data.source.FilmRepository
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity
import com.example.mysubmission1jetpack.vo.Resource


class DetailViewModel(private val filmRepository: FilmRepository): ViewModel() {
    val filmId = MutableLiveData<String>()


    fun setSelectedMovie(filmId: String){
        this.filmId.value = filmId
    }

    fun setFavoriteMovie(){
        val movieResource = detailMovies.value
        if (movieResource?.data != null){
            val state = !movieResource.data.favoriteMovie
            filmRepository.setMovieFavorite(movieResource.data, state)
        }
    }

    fun setFavoriteTvShow(){
        val tvShowResource = detailTvShow.value
        if (tvShowResource?.data != null){
            val state = !tvShowResource.data.favoriteTvShow
            filmRepository.setTvShowFavorite(tvShowResource.data, state)
        }
    }
    var detailTvShow: LiveData<Resource<TvShowEntity>> = Transformations.switchMap(filmId){ mTvshowId ->
        filmRepository.getDetailTvshow(mTvshowId)
    }
    var detailMovies: LiveData<Resource<MoviesEntity>> = Transformations.switchMap(filmId){ mMoviesId ->
        filmRepository.getDetailMovie(mMoviesId)
    }

}