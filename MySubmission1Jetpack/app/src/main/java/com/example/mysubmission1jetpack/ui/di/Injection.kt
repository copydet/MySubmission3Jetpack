package com.example.mysubmission1jetpack.ui.di

import android.content.Context
import com.example.mysubmission1jetpack.data.source.FilmRepository
import com.example.mysubmission1jetpack.data.source.local.room.LocalDataSource
import com.example.mysubmission1jetpack.data.source.local.room.MovieDatabase
import com.example.mysubmission1jetpack.data.source.local.room.TvShowDatabase
import com.example.mysubmission1jetpack.data.source.remote.RemoteDataSource
import com.example.mysubmission1jetpack.utils.AppExecutors
import com.example.mysubmission1jetpack.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): FilmRepository{

        val databaseMovie = MovieDatabase.getInstance(context)
        val databaseTvShow = TvShowDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(databaseMovie.movieDao(), databaseTvShow.tvShowDao())
        val appExecutors = AppExecutors()

        return FilmRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}