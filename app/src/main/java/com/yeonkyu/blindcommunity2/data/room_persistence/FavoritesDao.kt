package com.yeonkyu.blindcommunity2.data.room_persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(vararg post: Favorites)

    @Query("SELECT * FROM favorites")
    fun getAll(): List<Favorites>
}