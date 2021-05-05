package com.example.mysubmission1jetpack.ui.favorite.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysubmission1jetpack.R
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity
import com.example.mysubmission1jetpack.databinding.FragmentFavoriteMovieBinding
import com.example.mysubmission1jetpack.databinding.FragmentFavoriteTvShowBinding
import com.example.mysubmission1jetpack.ui.favorite.movies.FavoriteMovieViewModel
import com.example.mysubmission1jetpack.ui.favorite.movies.FavoriteMoviesAdapter
import com.example.mysubmission1jetpack.ui.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoriteTvShowFragment : Fragment(), FavoriteTvShowCallback {

    private var _fragmentTvShowBinding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _fragmentTvShowBinding
    private lateinit var viewModel: FavoriteTvShowViewModel
    private lateinit var adapter: FavoriteTvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTvShowBinding = FragmentFavoriteTvShowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteTvShowViewModel::class.java]

            adapter = FavoriteTvShowAdapter(this)
            binding?.progressBar?.visibility = View.VISIBLE
            viewModel.getTvShowFavorite().observe(this, { tvshow ->
                binding?.progressBar?.visibility = View.GONE
                adapter.submitList(tvshow)
            })
            binding?.rvFavoriteTvshow?.layoutManager = LinearLayoutManager(context)
            binding?.rvFavoriteTvshow?.setHasFixedSize(true)
            binding?.rvFavoriteTvshow?.adapter = adapter
        }
    }

    override fun onShareClick(tvshow : TvShowEntity) {
        if (activity != null){
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan Aplikasi ini ?")
                .setText("Check Film ${tvshow.title} di Aplikasi ini")
                .startChooser()
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val swipedPosition = viewHolder.adapterPosition
                val tvShowentity = adapter.getSwipedData(swipedPosition)
                tvShowentity?.let { viewModel.setFavoriteTvShow(it) }

                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok){ v ->
                    tvShowentity?.let { viewModel.setFavoriteTvShow(it) }
                }
                snackbar.show()
            }
        }
    })
}