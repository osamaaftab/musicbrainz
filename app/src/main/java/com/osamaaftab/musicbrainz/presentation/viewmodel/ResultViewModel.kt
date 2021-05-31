package com.osamaaftab.musicbrainz.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.osamaaftab.musicbrainz.domain.model.ApiResponse
import com.osamaaftab.musicbrainz.domain.model.PlaceModel
import com.osamaaftab.musicbrainz.domain.model.RequestModel
import com.osamaaftab.musicbrainz.domain.model.base.ErrorModel
import com.osamaaftab.musicbrainz.domain.usecase.GetPlacesUsecase
import com.osamaaftab.musicbrainz.domain.usecase.base.UseCaseResponse
import com.osamaaftab.musicbrainz.presentation.base.BaseViewModel

class ResultViewModel(private val useCase: GetPlacesUsecase) : BaseViewModel() {

    private val onProgressShow = MutableLiveData<Boolean>()
    private val onErrorShow = MutableLiveData<String>()
    private val onErrorState = MutableLiveData<Boolean>()
    private val onAddPin = MutableLiveData<PlaceModel>()

    fun getPlacesUseCaseResponse(query: String, offset: Int) = object : UseCaseResponse<ApiResponse> {
            override fun onSuccess(response: ApiResponse) {
                if (response.count > apiPageLimit && ((response.count + apiPageLimit - 1) / apiPageLimit) != offset + 1) {
                    val counter = offset + 1
                    onSearchPlaces(query, counter)
                }
                onProgressShow.value = false
                for (place in response.places) {
                    if (place.coordinates != null) {
                        onAddPin.value = place
                    }
                }
                Log.d(ContentValues.TAG, "result: $response")
            }


            override fun onError(errorModel: ErrorModel?) {
                Log.d(ContentValues.TAG, "error: $errorModel?.message code")
                onProgressShow.value = false
                onErrorShow.value = errorModel?.errorStatus?.name
                onErrorState.value = true
            }
        }

    fun onAddPin(): LiveData<PlaceModel> {
        return onAddPin
    }

    fun onErrorShow(): LiveData<String> {
        return onErrorShow
    }

    fun onErrorState(): LiveData<Boolean> {
        return onErrorState
    }

    fun onShowProgress() : LiveData<Boolean>{
        return onProgressShow
    }

    fun onSearchPlaces(query: String, offset: Int? = 0) {
        onProgressShow.value = true
        useCase.invoke(
            scope, RequestModel(offset!!, apiPageLimit, query), getPlacesUseCaseResponse(query, offset)
        )
    }
}