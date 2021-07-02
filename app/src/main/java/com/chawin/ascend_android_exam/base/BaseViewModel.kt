package com.chawin.ascend_android_exam.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent

abstract class BaseViewModel : ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    protected val _dialogError = LiveEvent<String>()
    val dialogError: LiveData<String> by lazy { _dialogError }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    protected val _loading = LiveEvent<Boolean>()
    val loading: LiveData<Boolean> by lazy { _loading }

    protected var _showEmptyLayout = LiveEvent<Boolean>()
    val showEmptyLayout: LiveData<Boolean> by lazy { _showEmptyLayout }
}