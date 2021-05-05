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
import com.example.mysubmission1jetpack.databinding.ActivityDetailFilmBinding
import com.example.mysubmission1jetpack.databinding.ContentDetailFilmBinding
import com.example.mysubmission1jetpack.ui.viewmodel.ViewModelFactory
import com.example.mysubmission1jetpack.vo.Status

class DetailFilmActivity : AppCompatActivity() {

    private lateinit var detailContentBinding : ContentDetailFilmBinding
    private lateinit var activityDetailMovieBinding: ActivityDetailFilmBinding

    private lateinit var viewModel: DetailViewModel
    private var menu: Menu? = null

    companion object{
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailMovieBinding = ActivityDetailFilmBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailMovieBinding.detailContent
        setContentView(activityDetailMovieBinding.root)
        setSupportActionBar(activityDetailMovieBinding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val extrasMovie = intent.extras
        if (extrasMovie != null) {
            val movieId = extrasMovie.getString(EXTRA_MOVIE)
            if (movieId != null) {
                viewModel.setSelectedMovie(movieId)

                viewModel.detailMovies.observe(this, { detailMovie ->
                    if (detailMovie != null){
                        when(detailMovie.status){
                            Status.LOADING -> activityDetailMovieBinding.progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> if (detailMovie.data != null){
                                activityDetailMovieBinding.progressBar.visibility = View.GONE
                                activityDetailMovieBinding.nestedScrollView.visibility = View.VISIBLE

                                populateMovie(detailMovie.data)
                            }
                            Status.ERROR -> {
                                activityDetailMovieBinding.progressBar.visibility = View.GONE
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

        viewModel.detailMovies.observe(this, { detailMovies ->
            if (detailMovies != null){
                when(detailMovies.status){
                    Status.LOADING -> activityDetailMovieBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (detailMovies.data != null){
                        activityDetailMovieBinding.progressBar.visibility = View.GONE
                        val state = detailMovies.data.favoriteMovie
                        setFavorite(state)
                    }
                    Status.ERROR -> {
                        activityDetailMovieBinding.progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite){
            viewModel.setFavoriteMovie()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavorite(state: Boolean){
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state){
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun populateMovie(movie: MoviesEntity) {
        detailContentBinding.textTitle.text = movie.title
        detailContentBinding.textDescription.text = movie.description
        detailContentBinding.releaseDate.text = resources.getString(R.string.release_date, movie.release)

        Glide.with(this)
                .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2${movie.imagePath}")
                .transform(RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailContentBinding.imagePoster)


    }

}
