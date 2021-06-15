package com.yeonkyu.blindcommunity2.usecase

import com.yeonkyu.blindcommunity2.AbstractKoinTest
import org.junit.Test
import com.yeonkyu.blindcommunity2.data.repository.PostRepository
import kotlinx.coroutines.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.koin.test.inject

class PostRepositoryTest: AbstractKoinTest() {

    private val repository by inject<PostRepository>()
    private val postId:String = "20201125_001913"

    @Before
    fun setup(){
        //변수 초기화가 필요하다면 @Test 전에 세팅할 수 있다.
    }

    @Test
    fun `getFreePostTest`() = runBlocking {

        val response = repository.getFreePost(postId)
        //assertTrue(response is ArrayList<*>)
    }
}