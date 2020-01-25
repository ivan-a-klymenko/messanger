package ru.bk.klim9.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.bk.klim9.chat.domain.type.Failure
import ru.bk.klim9.chat.domain.type.HandleOnce

abstract class BaseViewModel : ViewModel() {

    var failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData()
    var progressData: MutableLiveData<Boolean> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failureData.value = HandleOnce(failure)
        updateProgress(false)
    }

    protected fun updateProgress(progress: Boolean) {
        this.progressData.value = progress
    }
}