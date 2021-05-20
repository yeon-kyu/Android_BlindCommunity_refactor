package com.yeonkyu.blindcommunity2.usecase

import androidx.lifecycle.MutableLiveData
import com.yeonkyu.blindcommunity2.AbstractKoinTest
import com.yeonkyu.blindcommunity2.data.repository.PostRepository
import com.yeonkyu.blindcommunity2.ui.post.PostViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

class PostViewModelTest: AbstractKoinTest() {

    private val postViewModel: PostViewModel by inject()

    @Before
    fun setup(){
        postViewModel.type = 1
        postViewModel.postId.value = "20201125_001913"
    }

    @Test
    fun refreshPostTest(){
//        val postRepository = declareMock<PostRepository>{
//            //given(refreshPost())
//        }

        postViewModel.refreshPost()
        //assertEquals(MutableLiveData<Boolean>(false),postViewModel.isLoading.value)

    }
}