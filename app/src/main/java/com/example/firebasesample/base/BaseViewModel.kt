package com.example.firebasesample.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * View 에 상태가 변경되었음을 알린다.
     */
    private val _viewStateLiveData = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewStateLiveData

    protected fun viewStateChange(viewState: ViewState) {
        viewModelScope.launch {
            _viewStateLiveData.value = viewState
            _viewStateLiveData.value = null
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}

interface ViewState