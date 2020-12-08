package com.mumayank.aircoroutine

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class AirCoroutines {

    interface Callback {
        suspend fun aOnTask(coroutineScope: CoroutineScope): Boolean
        fun bOnSuccess()
        fun cOnError()
    }

    companion object {

        fun execute(
            activity: AppCompatActivity,
            isTaskIo: Boolean,
            callback: Callback?
        ) {
            val activityWeakReference = WeakReference(activity)
            val viewModel =
                ViewModelProvider(activity as ViewModelStoreOwner)[AirViewModel::class.java]

            viewModel.viewModelScope.launch(if (isTaskIo) Dispatchers.IO else Dispatchers.Default) {
                val result: Boolean
                try {
                    result = withContext(this.coroutineContext) {
                        callback?.aOnTask(viewModel.viewModelScope) ?: false
                    }
                    if (result) {
                        if (activityWeakReference.get() == null) {
                            return@launch
                        }
                        withContext(Dispatchers.Main) {
                            callback?.bOnSuccess()
                        }
                    } else {
                        if (activityWeakReference.get() == null) {
                            return@launch
                        }
                        withContext(Dispatchers.Main) {
                            callback?.cOnError()
                        }
                    }
                } catch (e: Exception) {
                    if (activityWeakReference.get() == null) {
                        return@launch
                    }
                    withContext(Dispatchers.Main) {
                        callback?.cOnError()
                    }
                }
            }
        }

    }

}