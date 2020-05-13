package com.mumayank.aircoroutineproject.sub

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mumayank.aircoroutine.AirCoroutines
import com.mumayank.aircoroutine.AirViewModel
import com.mumayank.aircoroutineproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import org.greenrobot.eventbus.EventBus

class SubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sub_activity)

        val airViewModel = AirCoroutines.getAirViewModelOfActivity(this)
        if (AirCoroutines.isAirViewModelOfActivityAlreadyInit(this).not()) {
            AirCoroutines.initAirViewModelOfActivity(this)
            airViewModel.any = 0
            beginTask(
                this,
                airViewModel
            )
        }

    }

    companion object {

        fun beginTask(activity: Activity, airViewModel: AirViewModel) {

            AirCoroutines.perform(
                activity,
                AirCoroutines.TaskType.COMPUTATION,
                object : AirCoroutines.Callback {

                    override suspend fun onTask(coroutineScope: CoroutineScope): Boolean {
                        delay(1)
                        return true
                    }

                    override fun onSuccess() {
                        airViewModel.any = (airViewModel.any as Int) + 1
                        EventBus.getDefault().postSticky(OnIndexUpdatedEvent())
                        beginTask(
                            activity,
                            airViewModel
                        )
                    }

                    override fun onFailure(errorMessage: String) {
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
                    }

                })

        }

    }
}
