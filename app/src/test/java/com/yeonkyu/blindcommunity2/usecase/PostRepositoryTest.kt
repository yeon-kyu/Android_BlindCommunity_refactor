package com.yeonkyu.blindcommunity2.usecase

import com.yeonkyu.blindcommunity2.AbstractKoinTest
import com.yeonkyu.blindcommunity2.data.api.PostService
import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import com.yeonkyu.blindcommunity2.data.repository.PostRepository
import kotlinx.coroutines.*
import org.junit.Assert.assertTrue
import org.junit.Before

class PostRepositoryTest: AbstractKoinTest() {

    private lateinit var repository: PostRepository
    private val service: PostService = mock()

    private val postId:String = "20201125_001913"

    @Before
    fun setup(){
        repository = PostRepository(service)
    }

    @Test
    fun getFreePostTest(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getFreePost(postId)
            assertTrue(response is ArrayList<*>)
        }
    }
}