package com.example.mysubmission1jetpack.ui.favorite.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmission1jetpack.R
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.databinding.ItemFavoriteMovieBinding
import com.example.mysubmission1jetpack.databinding.ItemsMovieBinding
import com.example.mysubmission1jetpack.ui.detail.DetailFilmActivity

class FavoriteMoviesAdapter(private val callback: FavoriteMoviesCallback): PagedListAdapter<MoviesEntity, FavoriteMoviesAdapter.FavoriteMovieViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MoviesEntity>(){
            override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMovieViewHolder {
        val itemMoviesBinding = ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(itemMoviesBinding)
    }

    override fun onBindViewHolder(
        holder: FavoriteMovieViewHolder,
        position: Int
    ) {
        val movies = getItem(position)
        if (movies != null){
            holder.bind(movies)
        }
    }
    fun getSwipedData(swipedPosition: Int): MoviesEntity? = getItem(swipedPosition)

    inner class FavoriteMovieViewHolder(private val binding: ItemFavoriteMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movies: MoviesEntity){
            with(binding){
                tvItemTitle.text = movies.title
                tvItemDate.text = itemView.resources.getString(R.string.release_date, movies.release)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFilmActivity::class.java)
                    intent.putExtra(DetailFilmActivity.EXTRA_MOVIE, movies.movieId)
                    itemView.context.startActivity(intent)
                }
                imgShare.setOnClickListener { callback.onShareClick(movies) }
                Glide.with(itemView.context)
                    .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2${movies.imagePath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }

}