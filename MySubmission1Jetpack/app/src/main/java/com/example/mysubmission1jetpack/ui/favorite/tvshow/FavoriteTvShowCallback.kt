package com.example.mysubmission1jetpack.ui.favorite.tvshow

import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity

interface FavoriteTvShowCallback {
    fun onShareClick(tvshow: TvShowEntity)
}