package com.osamaaftab.musicbrainz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.osamaaftab.musicbrainz.data.repository.MusicRepositoryImp
import com.osamaaftab.musicbrainz.domain.model.ApiResponse
import com.osamaaftab.musicbrainz.domain.model.RequestModel
import com.osamaaftab.musicbrainz.domain.model.base.ErrorModel
import com.osamaaftab.musicbrainz.domain.usecase.GetPlacesUsecase
import com.osamaaftab.musicbrainz.util.handler.ApiErrorHandle
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetPlacesUsecaseUT {

    lateinit var getPlacesUsecase: GetPlacesUsecase

    @MockK
    lateinit var musicRepositoryImp: MusicRepositoryImp

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        val apiErrorHandle = mockk<ApiErrorHandle>()
        getPlacesUsecase = GetPlacesUsecase(musicRepositoryImp, apiErrorHandle)
    }


    @Test
    fun getApiResponseForPlacesSuccess() = runBlocking {
        val apiResponse = mockk<ApiResponse>()
        val request = mockk<RequestModel>()

        every { runBlocking { musicRepositoryImp.getPlaces(request) } } returns (apiResponse)
        val expectedPost = musicRepositoryImp.getPlaces(request)
        every { runBlocking { getPlacesUsecase.run(request) } } returns (apiResponse)
        val expectedResult = getPlacesUsecase.run(request)
        assertEquals(expectedPost, expectedResult)
    }
}