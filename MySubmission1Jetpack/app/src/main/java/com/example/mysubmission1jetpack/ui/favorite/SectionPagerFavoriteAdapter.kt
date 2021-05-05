package com.example.mysubmission1jetpack.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mysubmission1jetpack.R
import com.example.mysubmission1jetpack.ui.favorite.movies.FavoriteMovieFragment
import com.example.mysubmission1jetpack.ui.favorite.tvshow.FavoriteTvShowFragment
import com.example.mysubmission1jetpack.ui.home.SectionPagerAdapter

class SectionPagerFavoriteAdapter(private val mFavorite: Context, fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object{
        @StringRes
        private val TAB_TITLE_FAV = intArrayOf(R.string.fav_movie, R.string.fav_tvshow)
    }

    override fun getCount(): Int = TAB_TITLE_FAV.size

    override fun getItem(position: Int): Fragment =
        when(position){
            0 -> FavoriteMovieFragment()
            1 -> FavoriteTvShowFragment()
            else -> Fragment()
        }
    override fun getPageTitle(position: Int): CharSequence? = mFavorite.resources.getString(
        SectionPagerFavoriteAdapter.TAB_TITLE_FAV[position])
}