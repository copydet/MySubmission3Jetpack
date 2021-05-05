package com.example.mysubmission1jetpack.ui.academy

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmission1jetpack.R
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.databinding.ItemsMovieBinding
import com.example.mysubmission1jetpack.ui.detail.DetailFilmActivity

class MovieAdapter: PagedListAdapter<MoviesEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK = object  : DiffUtil.ItemCallback<MoviesEntity>(){
            override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
    class MovieViewHolder(private val binding: ItemsMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MoviesEntity){
            with(binding){
                tvItemTitle.text = movie.title
                tvItemDate.text = itemView.resources.getString(R.string.release_date, movie.release)
                itemView.setOnClickListener {
                    val moveDetail = Intent(itemView.context, DetailFilmActivity::class.java)
                    moveDetail.putExtra(DetailFilmActivity.EXTRA_MOVIE, movie.movieId)
                    itemView.context.startActivity(moveDetail)
                }
                Glide.with(itemView.context)
                        .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2${movie.imagePath}")
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                        .into(imgPoster)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bind(movie)
        }
    }
}
