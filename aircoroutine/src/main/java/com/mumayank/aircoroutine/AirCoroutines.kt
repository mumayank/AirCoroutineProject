package com.mumayank.aircoroutine

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AirCoroutines {

    enum class TaskType {
        UI,
        IO_WITH_DISK_OR_NETWORK,
        COMPUTATION
    }

    interface Callback {
        suspend fun onTask(coroutineScope: CoroutineScope): Boolean
        fun onSuccess()
        fun onFailure(errorMessage: String)
    }

    companion object {

        fun <T : ViewModel> perform(activity: Activity, taskType: TaskType, callback: Callback?, viewModelClass: Class<T>) {
            val model = getViewModel(activity, viewModelClass)
            model.viewModelScope.launch(
                when (taskType) {
                    TaskType.UI -> Dispatchers.Main
                    TaskType.IO_WITH_DISK_OR_NETWORK -> Dispatchers.IO
                    else -> Dispatchers.Default
                }
            ) {
                try {
                    val result = this.async {
                        callback?.onTask(model.viewModelScope) ?: false
                    }
                    if (result.await()) {
                        this.launch(Dispatchers.Main) {
                            callback?.onSuccess()
                        }
                    } else {
                        this.launch(Dispatchers.Main) {
                            callback?.onFailure("Developer returned false as the task result")
                        }
                    }
                } catch (e: Exception) {
                    this.launch(Dispatchers.Main) {
                        callback?.onFailure(e.toString())
                    }
                }
            }
        }

        fun perform(activity: Activity, taskType: TaskType, callback: Callback?) {
            perform(activity, taskType, callback, AirViewModel::class.java)
        }

        fun <T : ViewModel> getViewModel(activity: Activity?, viewModelClass: Class<T>): T {
            return ViewModelProvider(activity as ViewModelStoreOwner)[viewModelClass]
        }

        fun getAirViewModelOfActivity(activity: Activity): AirViewModel {
            return ViewModelProvider(activity as ViewModelStoreOwner)[AirViewModel::class.java]
        }

        fun isAirViewModelOfActivityAlreadyInit(activity: Activity): Boolean {
            val model =
                getAirViewModelOfActivity(
                    activity
                )
            return model.isAlreadyInit
        }

        fun initAirViewModelOfActivity(activity: Activity) {
            val model =
                getAirViewModelOfActivity(
                    activity
                )
            model.isAlreadyInit = true
        }

    }

}