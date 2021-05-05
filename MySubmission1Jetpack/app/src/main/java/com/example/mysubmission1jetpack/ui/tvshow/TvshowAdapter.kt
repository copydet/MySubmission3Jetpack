package com.example.mysubmission1jetpack.ui.tvshow

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
import com.example.mysubmission1jetpack.databinding.ItemsTvshowBinding
import com.example.mysubmission1jetpack.ui.detail.DetailTvshowActivity

class TvshowAdapter: PagedListAdapter<TvShowEntity, TvshowAdapter.FilmViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>(){
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.tvshowId == newItem.tvshowId
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class FilmViewHolder(private val binding: ItemsTvshowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tvshow: TvShowEntity){
            with(binding){
                tvItemTitle.text = tvshow.title
                tvItemDate.text = itemView.resources.getString(R.string.release_date, tvshow.release)
                itemView.setOnClickListener {
                    val moveDetail = Intent(itemView.context, DetailTvshowActivity::class.java)
                    moveDetail.putExtra(DetailTvshowActivity.EXTRA_TVSHOW, tvshow.tvshowId)
                    itemView.context.startActivity(moveDetail)
                }
                Glide.with(itemView.context)
                        .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2${tvshow.imagePath}")
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                        .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val itemsTvshowBinding = ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(itemsTvshowBinding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val tvshow = getItem(position)
        if (tvshow != null){
            holder.bind(tvshow)
        }
    }

}