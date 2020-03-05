package com.mumayank.aircoroutine

import android.app.Activity
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
                var taskResult = TaskResult.FAILURE
                val deferred =
                    async(if (callback?.getTaskType() == TaskType.IO) Dispatchers.IO else Dispatchers.Default) {
                        taskResult = try {
                            callback?.doTaskInBg(viewModel) ?: TaskResult.FAILURE
                        } catch (e: Exception) {
                            TaskResult.FAILURE
                        }
                    }
                deferred.await()
                if (taskResult == TaskResult.SUCCESS) {
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

    }

    enum class TaskResult {
        SUCCESS,
        FAILURE
    }

    enum class TaskType {
        IO,
        CALCULATIONS
    }

    interface Callback {
        suspend fun doTaskInBg(viewModel: ViewModel): TaskResult?
        fun getTaskType(): TaskType?
        fun onSuccess(viewModel: ViewModel)
        fun onFailure(viewModel: ViewModel)
    }

}