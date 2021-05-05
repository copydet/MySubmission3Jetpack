package com.example.mysubmission1jetpack.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tvshowentities")
data class TvShowEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvshowId")
    var tvshowId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "realese")
    var release: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "favoriteTvShow")
    var favoriteTvShow: Boolean = false,

    @ColumnInfo(name = "imagePath")
    var imagePath: String
        )