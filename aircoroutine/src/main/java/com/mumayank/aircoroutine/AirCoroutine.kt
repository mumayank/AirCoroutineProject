package com.mumayank.aircoroutine

import android.app.Activity
import android.telecom.Call
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AirCoroutine {

    companion object {

        fun <T : ViewModel> getViewModel(activity: Activity?, viewModelClass: Class<T>): T {
            return ViewModelProvider(activity as ViewModelStoreOwner)[viewModelClass]
        }

        fun execute(viewModel: ViewModel?, callback: Callback?) {
            viewModel?.viewModelScope?.launch {
                var taskResult: Boolean? = true
                val deferred =
                    async(if (callback?.isTaskTypeCalculation() == false) Dispatchers.IO else Dispatchers.Default) {
                        taskResult = try {
                            callback?.doTaskInBg(viewModel) ?: false
                        } catch (e: Exception) {
                            false
                        }
                    }
                deferred.await()
                if (taskResult == true) {
                    try {
                        callback?.onSuccess(viewModel)
                    } catch (e: Exception) {
                        // ignore
                    }
                } else {
                    try {
                        callback?.onFailure(viewModel)
                    } catch (e: Exception) {
                        // ignore
                    }
                }
            }
        }

        fun execute(activity: Activity?, callback: Callback?) {
            execute(getViewModel(activity, AirViewModel::class.java), callback)
        }

    }

    enum class TaskResult {
        SUCCESS,
        FAILURE
    }

    interface Callback {
        suspend fun doTaskInBg(viewModel: ViewModel): Boolean?
        fun isTaskTypeCalculation(): Boolean
        fun onSuccess(viewModel: ViewModel)
        fun onFailure(viewModel: ViewModel)
    }

}