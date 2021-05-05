package com.example.mysubmission1jetpack.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.mysubmission1jetpack.R
import com.example.mysubmission1jetpack.databinding.ActivityFavoriteBinding
import com.example.mysubmission1jetpack.ui.home.MoviesActivity

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val sectionPagerAdapter = SectionPagerFavoriteAdapter(this, supportFragmentManager)
        binding?.viewPagerFavorite?.adapter = sectionPagerAdapter
        binding?.tabsFavorite?.setupWithViewPager(binding?.viewPagerFavorite)

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(itemId: Int) {
        when(itemId){
            R.id.home_film ->{
                val homeFilm = Intent(this, MoviesActivity::class.java)
                startActivity(homeFilm)
            }
            R.id.favorite_film ->{
                val favoriteFilm = Intent(this, FavoriteActivity::class.java)
                startActivity(favoriteFilm)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}