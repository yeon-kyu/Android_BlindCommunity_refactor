package com.yeonkyu.blindcommunity2.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.blindcommunity2.data.entities.BoardInfo
import com.yeonkyu.blindcommunity2.data.room_persistence.Favorites
import com.yeonkyu.blindcommunity2.data.room_persistence.FavoritesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val db: FavoritesDao): ViewModel() {

    private val _favoritePostList = MutableLiveData<List<BoardInfo>>()
    val favoritePostList : LiveData<List<BoardInfo>>
        get() = _favoritePostList

    fun loadFavoritePostList(){
        viewModelScope.launch(Dispatchers.Default) {
            val favoritesList: List<Favorites> = db.getAllPost()
            val boardInfoList: MutableList<BoardInfo> = mutableListOf()
            for(item in favoritesList){
                val type = when(item.type){
                    "1" -> "자유 게시판"
                    "2" -> "정보 게시판"
                    "3" -> "취업 게시판"
                    else -> "-"
                }
                boardInfoList.add(BoardInfo(postId = item.postId, nickname = item.nickname, title = item.title, type = type))
            }
            _favoritePostList.postValue(boardInfoList)
        }
    }

}