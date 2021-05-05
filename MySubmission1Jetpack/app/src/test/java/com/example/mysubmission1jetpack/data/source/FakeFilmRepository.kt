package com.example.mysubmission1jetpack.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mysubmission1jetpack.data.source.local.entity.MoviesEntity
import com.example.mysubmission1jetpack.data.source.local.entity.TvShowEntity
import com.example.mysubmission1jetpack.data.source.local.room.LocalDataSource
import com.example.mysubmission1jetpack.data.source.remote.ApiResponse
import com.example.mysubmission1jetpack.data.source.remote.RemoteDataSource
import com.example.mysubmission1jetpack.data.source.remote.response.Response
import com.example.mysubmission1jetpack.utils.AppExecutors
import com.example.mysubmission1jetpack.vo.Resource

class FakeFilmRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): FilmDataSource{


    override fun getAllMovie(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object  : NetworkBoundResource<PagedList<MoviesEntity>, List<Response>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> {
                val configMovie = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), configMovie).build()
            }

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<Response>>> =
                remoteDataSource.getAllMovie()

            public override fun saveCallResult(data: List<Response>) {
                val movieList = ArrayList<MoviesEntity>()
                for (response in data){
                    val movie = MoviesEntity(response.id,
                        response.title,
                        response.realese,
                        response.description,
                        response.favorite,
                        response.poster)
                    movieList.add(movie)
                }

                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<Response>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val configTvShow = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShow(), configTvShow).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<Response>>> =
                remoteDataSource.getAllTvshow()

            public override fun saveCallResult(data: List<Response>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data){
                    val tvshow = TvShowEntity(response.id,
                        response.title,
                        response.realese,
                        response.description,
                        response.favorite,
                        response.poster)
                    tvShowList.add(tvshow)
                }
                localDataSource.insertTvShow(tvShowList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(movieId: String): LiveData<Resource<MoviesEntity>> {
        return object : NetworkBoundResource<MoviesEntity, List<Response>>(appExecutors){
            override fun loadFromDB(): LiveData<MoviesEntity> =
                localDataSource.getDetailMovie(movieId)

            override fun shouldFetch(data: MoviesEntity?): Boolean =
                data != null && data.title == "" && data.release == ""

            override fun createCall(): LiveData<ApiResponse<List<Response>>> =
                remoteDataSource.getDetailMovie(movieId)

            override fun saveCallResult(data: List<Response>) {
                val movieList = ArrayList<MoviesEntity>()
                for (response in data){
                    val movies = MoviesEntity(
                        response.id,
                        response.title,
                        response.realese,
                        response.description,
                        response.favorite,
                        response.poster
                    )
                    movieList.add(movies)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getDetailTvshow(tvshowId: String): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, List<Response>>(appExecutors){
            override fun loadFromDB(): LiveData<TvShowEntity> =
                localDataSource.getDetailTvShow(tvshowId)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data != null && data.title == "" && data.release == ""

            override fun createCall(): LiveData<ApiResponse<List<Response>>> =
                remoteDataSource.getDetailTvShow(tvshowId)

            override fun saveCallResult(data: List<Response>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data){
                    val tvshow = TvShowEntity(
                        response.id,
                        response.title,
                        response.realese,
                        response.description,
                        response.favorite,
                        response.poster
                    )
                    tvShowList.add(tvshow)
                }
                localDataSource.insertTvShow(tvShowList)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MoviesEntity>> {
        val configFavMovie = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getMovieFavorite(),configFavMovie).build()
    }

    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
        val configFavTvShow = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getTvShowFavorite(), configFavTvShow).build()
    }

    override fun setMovieFavorite(movie: MoviesEntity, state: Boolean) =
        appExecutors.diskIO().execute{ localDataSource.setMovieFavorite(movie, state)}


    override fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setTvShowFavorite(tvShow, state) }

}