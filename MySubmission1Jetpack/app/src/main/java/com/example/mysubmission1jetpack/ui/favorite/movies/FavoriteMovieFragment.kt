package com.example.mysubmission1jetpack.ui.favorite.movies

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
import com.example.mysubmission1jetpack.databinding.FragmentFavoriteMovieBinding
import com.example.mysubmission1jetpack.ui.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteMovieFragment : Fragment(), FavoriteMoviesCallback {

    private var _fragmentMoviesBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _fragmentMoviesBinding
    private lateinit var viewModel: FavoriteMovieViewModel
    private lateinit var adapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMoviesBinding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteMovie)

        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

            adapter = FavoriteMoviesAdapter(this)
            binding?.progressBar?.visibility = View.VISIBLE
            viewModel.getFavoriteMovies().observe(this, { movies ->
                binding?.progressBar?.visibility = View.GONE
                adapter.submitList(movies)

            })
            binding?.rvFavoriteMovie?.layoutManager = LinearLayoutManager(context)
            binding?.rvFavoriteMovie?.setHasFixedSize(true)
            binding?.rvFavoriteMovie?.adapter = adapter
        }
    }

    override fun onShareClick(movies: MoviesEntity) {
        if (activity != null){
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan Aplikasi ini ?")
                .setText("Check Film ${movies.title} di Aplikasi ini")
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
        ): Boolean =
            true


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val swipePosition = viewHolder.adapterPosition
                val movieEntity = adapter.getSwipedData(swipePosition)
                movieEntity?.let { viewModel.setFavoriteMovie(it) }

                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok){ v ->
                    movieEntity?.let { viewModel.setFavoriteMovie(it) }
                }
                snackbar.show()
            }
        }
    })

}