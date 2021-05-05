package com.example.mysubmission1jetpack.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmission1jetpack.R
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity
import com.example.mysubmission1jetpack.databinding.ActivityDetailTvshowBinding
import com.example.mysubmission1jetpack.databinding.ContentDetailFilmBinding
import com.example.mysubmission1jetpack.ui.viewmodel.ViewModelFactory
import com.example.mysubmission1jetpack.vo.Status

class DetailTvshowActivity : AppCompatActivity() {

    private lateinit var detailContentBinding : ContentDetailFilmBinding
    private lateinit var activityDetailTvShowBinding: ActivityDetailTvshowBinding
    private lateinit var viewModel: DetailViewModel
    private var menu: Menu? = null
    companion object{
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailTvShowBinding = ActivityDetailTvshowBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailTvShowBinding.detailContent
        setContentView(activityDetailTvShowBinding.root)
        setSupportActionBar(activityDetailTvShowBinding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val extrasTvshow = intent.extras
        if (extrasTvshow != null) {
            val tvshowId = extrasTvshow.getString(EXTRA_TVSHOW)
            if (tvshowId != null) {
                viewModel.setSelectedMovie(tvshowId)

                viewModel.detailTvShow.observe(this, { detailTvShow ->
                    if (detailTvShow != null){
                        when(detailTvShow.status){
                            Status.LOADING -> activityDetailTvShowBinding.progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> if (detailTvShow.data != null){
                                activityDetailTvShowBinding.progressBar.visibility = View.GONE
                                activityDetailTvShowBinding.contentTvShow.visibility = View.VISIBLE

                                populateTvShow(detailTvShow.data)
                            }
                            Status.ERROR ->{
                                activityDetailTvShowBinding.progressBar.visibility = View.GONE
                                Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.detailTvShow.observe(this, { detailTvShow ->
            if (detailTvShow != null){
                when(detailTvShow.status){
                    Status.LOADING -> activityDetailTvShowBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (detailTvShow.data != null){
                        activityDetailTvShowBinding.progressBar.visibility = View.GONE
                        val state = detailTvShow.data.favoriteTvShow
                        setFavorite(state)
                    }
                    Status.ERROR -> {
                        activityDetailTvShowBinding.progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite){
            viewModel.setFavoriteTvShow()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavorite(state: Boolean){
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state){
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        }else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
        }
    }


    private fun populateTvShow(tvshow: TvShowEntity) {
        detailContentBinding.textTitle.text = tvshow.title
        detailContentBinding.textDescription.text = tvshow.description
        detailContentBinding.releaseDate.text = resources.getString(R.string.release_date, tvshow.release)

        Glide.with(this)
            .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2${tvshow.imagePath}")
            .transform(RoundedCorners(20))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
            .into(detailContentBinding.imagePoster)

        }
    }

