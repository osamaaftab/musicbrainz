package com.osamaaftab.musicbrainz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.osamaaftab.musicbrainz.domain.model.ApiResponse
import com.osamaaftab.musicbrainz.domain.model.CoordinatesModel
import com.osamaaftab.musicbrainz.domain.model.PlaceModel
import com.osamaaftab.musicbrainz.domain.usecase.GetPlacesUsecase
import com.osamaaftab.musicbrainz.presentation.viewmodel.ResultViewModel
import com.osamaaftab.musicbrainz.util.handler.ApiErrorHandle
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ResultViewModelUT {

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    lateinit var getVenuesUseCase: GetPlacesUsecase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mapViewModel: ResultViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        mapViewModel = ResultViewModel(getVenuesUseCase)
    }

    @Test
    fun onSearch() {
        mapViewModel.onSearchPlaces("Hi there")
        val expectedValue = mapViewModel.onShowProgress().value
        Assert.assertEquals(true, expectedValue)
    }

    @Test
    fun onSuccess() {
        val places: MutableList<PlaceModel> = mutableListOf()
        var placeItem = getPlaceModelMock()
        places.add(placeItem)
        val result = ApiResponse(places, 1)
        mapViewModel.getPlacesUseCaseResponse("Hello", 0).onSuccess(result)
        val response = mapViewModel.onAddPin().value
        val state = mapViewModel.onShowProgress()
        Assert.assertEquals(false, state.value)
        Assert.assertEquals(placeItem, response)
    }

    @Test
    fun onFails() {
        val apiErrorHandle = ApiErrorHandle()
        val throwable = mockk<Throwable>()
        apiErrorHandle.traceErrorException(throwable)
        mapViewModel.getPlacesUseCaseResponse("Hello", 0)
            .onError(apiErrorHandle.traceErrorException(throwable))
        val state = mapViewModel.onErrorState().value
        val progress = mapViewModel.onShowProgress().value
        val msg = mapViewModel.onErrorShow().value
        Assert.assertEquals("BAD_RESPONSE", msg)
        Assert.assertEquals(true, state)
        Assert.assertEquals(false, progress)

    }

    private fun getPlaceModelMock() : PlaceModel{
        return PlaceModel("123","venue",10,"example","123 streets", CoordinatesModel("1233332","23422222"),null)
    }
}