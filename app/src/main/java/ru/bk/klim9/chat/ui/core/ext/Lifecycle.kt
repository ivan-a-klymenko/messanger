package ru.bk.klim9.chat.ui.core.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.bk.klim9.chat.domain.type.Failure
import ru.bk.klim9.chat.domain.type.HandleOnce

fun <T : Any, L : LiveData<T>> LifecycleOwner.onSuccess(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<HandleOnce<Failure>>> LifecycleOwner.onFailure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this, Observer {
        it.getContentIfNotHandled()?.let(body)
    })
