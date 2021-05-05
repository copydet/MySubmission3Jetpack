package com.example.revisigame.core.data

import com.example.revisigame.core.data.source.local.LocalDataSource
import com.example.revisigame.core.data.source.remote.RemoteDataSource
import com.example.revisigame.core.data.source.remote.network.ApiResponse
import com.example.revisigame.core.data.source.remote.response.ResultsItem
import com.example.revisigame.core.domain.model.Games
import com.example.revisigame.core.domain.repository.IGamesRepository
import com.example.revisigame.core.utils.AppExecutors
import com.example.revisigame.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GamesRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IGamesRepository {
    companion object {
        @Volatile
        private var instance: GamesRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): GamesRepository =
            instance ?: synchronized(this) {
                instance ?: GamesRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllGames(): Flow<Resource<List<Games>>> =
        object : NetworkBoundResource<List<Games>, List<ResultsItem>>(){
            override fun loadFromDB(): Flow<List<Games>> {
                return localDataSource.getAllGames().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Games>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getAllGames()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val gamesList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gamesList)
            }
        }.asFlow()

    override fun getWishListGame(): Flow<List<Games>> {
        return localDataSource.getWishList().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setWishListGame(games: Games, state: Boolean) {
        val gamesEntity = DataMapper.mapDomainToEntities(games)
        appExecutors.diskIO().execute{ localDataSource.setWishList(gamesEntity, state) }
    }
}