package com.yeonkyu.blindcommunity2.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.blindcommunity2.data.repository.BoardRepository
import com.yeonkyu.blindcommunity2.data.room_persistence.Favorites
import com.yeonkyu.blindcommunity2.data.room_persistence.FavoritesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: BoardRepository, private val db: FavoritesDao): ViewModel() {

    private val _favoritePostList = MutableLiveData<List<Favorites>>()
    val favoritePostList : LiveData<List<Favorites>>
        get() = _favoritePostList

    fun loadFavoritePostList(){
        viewModelScope.launch(Dispatchers.Default) {
            val favoritesList: List<Favorites> = db.getAll()
            _favoritePostList.postValue(favoritesList)
        }
    }

}