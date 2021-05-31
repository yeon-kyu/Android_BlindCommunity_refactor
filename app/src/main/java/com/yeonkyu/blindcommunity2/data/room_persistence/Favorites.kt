package com.yeonkyu.blindcommunity2.data.room_persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorites(
    @PrimaryKey(autoGenerate = true) val postId: String,
    @ColumnInfo(name = "nickname") val nickname:String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type") val type: String
)