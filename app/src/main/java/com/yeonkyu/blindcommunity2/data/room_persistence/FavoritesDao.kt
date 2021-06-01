package com.yeonkyu.blindcommunity2.data.room_persistence

import androidx.room.*

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(vararg post: Favorites)

    @Query("SELECT * FROM favorites")
    fun getAllPost(): List<Favorites>

    @Delete
    fun deletePost(favorite:Favorites)
}