package com.yeonkyu.blindcommunity2.usecase

import com.google.gson.internal.LinkedTreeMap
import com.yeonkyu.blindcommunity2.AbstractKoinTest
import com.yeonkyu.blindcommunity2.data.repository.PostRepository
import com.yeonkyu.blindcommunity2.data.room_persistence.FavoritesDao
import com.yeonkyu.blindcommunity2.ui.post.PostViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class PostViewModelTest: AbstractKoinTest() {

    private lateinit var postViewModel:PostViewModel
    private val repository: PostRepository = mock()
    private val dao: FavoritesDao = mock()

    @Before
    fun setup(){
        postViewModel = PostViewModel(repository, dao)
        postViewModel.type = 1
        postViewModel.postId.value = "20201125_001913"
    }

    @Test
    fun refreshPostTest(){
        runBlocking {

            val mockData = ArrayList<LinkedTreeMap<String, String>>()
            val treeMap = LinkedTreeMap<String,String>()
            treeMap["nickname"] = "yk"
            treeMap["title"] = "오늘도"
            treeMap["content"] = "special cafe latte"
            mockData.add(treeMap)

            //`when`(repository.getFreePost("20201125_001913")).thenReturn(mockData)
            postViewModel.refreshPost()
            verify(repository, times(1)).getFreePost("20201125_001913")

            delay(1000)

        }
        assertTrue(postViewModel.nickname.value=="yk")
        assertTrue(postViewModel.title.value=="오늘도")
        assertTrue(postViewModel.content.value=="special cafe latte")
    }
}

