package com.example.mysubmission1jetpack.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieentities")
data class MoviesEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "movieId")
        var movieId: String,

        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "realese")
        var release: String,

        @ColumnInfo(name = "description")
        var description: String,

        @ColumnInfo(name = "favoriteMovie")
        var favoriteMovie: Boolean = false,

        @ColumnInfo(name = "imagePath")
        var imagePath: String
)
