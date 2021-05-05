package com.example.mysubmission1jetpack.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mysubmission1jetpack.data.source.remote.response.Response
import com.example.mysubmission1jetpack.utils.EspressoIdlingResources
import com.example.mysubmission1jetpack.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){

    private val handler = Handler(Looper.getMainLooper())

    companion object{
        private const val SERVICE_LATENCY_IN_MILLIS : Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this){
                RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovie(): LiveData<ApiResponse<List<Response>>> {
        EspressoIdlingResources.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<Response>>>()
        handler.postDelayed({
            resultMovie.value = ApiResponse.success(jsonHelper.loadMovie())
            EspressoIdlingResources.decrement()},
            SERVICE_LATENCY_IN_MILLIS)
        return resultMovie
    }
    fun getAllTvshow(): LiveData<ApiResponse<List<Response>>>{
        EspressoIdlingResources.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<Response>>>()
        handler.postDelayed({
            resultTvShow.value = ApiResponse.success(jsonHelper.loadTvShow())
            EspressoIdlingResources.decrement()}, SERVICE_LATENCY_IN_MILLIS)
        return resultTvShow
    }

    fun getDetailMovie(movieId: String): LiveData<ApiResponse<List<Response>>>{
        EspressoIdlingResources.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<List<Response>>>()
        handler.postDelayed({
            resultDetailMovie.value = ApiResponse.success(jsonHelper.loadMovie())
            EspressoIdlingResources.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultDetailMovie
    }
    fun getDetailTvShow(movieId: String): LiveData<ApiResponse<List<Response>>>{
        EspressoIdlingResources.increment()
        val resultDetailTvShow = MutableLiveData<ApiResponse<List<Response>>>()
        handler.postDelayed({
            resultDetailTvShow.value = ApiResponse.success(jsonHelper.loadTvShow())
            EspressoIdlingResources.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultDetailTvShow
    }

    interface LoadMovieCallback {
        fun onAllMovieReceived(movieResponse: List<Response>)
    }
    interface LoadTvShowCallback{
        fun onAllTvShowReceived(tvshowResponse: List<Response>)
    }
}