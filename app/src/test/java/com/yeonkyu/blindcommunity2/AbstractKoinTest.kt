package com.yeonkyu.blindcommunity2

import com.yeonkyu.blindcommunity2.di.repositoryModule
import com.yeonkyu.blindcommunity2.di.viewModelModule
import org.junit.Rule
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

abstract class AbstractKoinTest: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(listOf(repositoryModule, viewModelModule))
    }

    @get:Rule
    val mockProvider = MockProviderRule.create {
        clazz -> Mockito.mock(clazz.java)
    }
}