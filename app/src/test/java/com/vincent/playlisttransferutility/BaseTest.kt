package com.vincent.playlisttransferutility

import org.junit.After
import org.mockito.Mockito

open class BaseTest {

    protected fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    protected fun <T> uninitialized(): T = null as T

    @After
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}