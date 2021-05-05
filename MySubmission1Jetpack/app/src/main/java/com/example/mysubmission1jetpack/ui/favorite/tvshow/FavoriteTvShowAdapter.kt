package com.example.mysubmission1jetpack.ui.favorite.tvshow

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
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity
import com.example.mysubmission1jetpack.databinding.ItemFavoriteMovieBinding
import com.example.mysubmission1jetpack.databinding.ItemFavoriteTvshowBinding
import com.example.mysubmission1jetpack.ui.detail.DetailFilmActivity
import com.example.mysubmission1jetpack.ui.detail.DetailTvshowActivity
import com.example.mysubmission1jetpack.ui.favorite.movies.FavoriteMoviesAdapter

class FavoriteTvShowAdapter(private val callback: FavoriteTvShowCallback): PagedListAdapter<TvShowEntity, FavoriteTvShowAdapter.FavoriteTvShowViewHolder>(DIFF_CALLBACK){
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>(){
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.tvshowId == newItem.tvshowId
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteTvShowViewHolder {
        val itemTvShowBinding = ItemFavoriteTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteTvShowViewHolder(itemTvShowBinding)
    }

    override fun onBindViewHolder(
        holder: FavoriteTvShowViewHolder,
        position: Int
    ) {
        val tvshow = getItem(position)
        if (tvshow != null){
            holder.bind(tvshow)
        }
    }

    fun getSwipedData(swipedPosition: Int): TvShowEntity? = getItem(swipedPosition)

    inner class FavoriteTvShowViewHolder(private val binding: ItemFavoriteTvshowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tvshow: TvShowEntity){
            with(binding){
                tvItemTitle.text = tvshow.title
                tvItemDate.text = itemView.resources.getString(R.string.release_date, tvshow.release)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvshowActivity::class.java)
                    intent.putExtra(DetailTvshowActivity.EXTRA_TVSHOW, tvshow.tvshowId)
                    itemView.context.startActivity(intent)
                }
                imgShare.setOnClickListener { callback.onShareClick(tvshow) }
                Glide.with(itemView.context)
                    .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2${tvshow.imagePath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }
}