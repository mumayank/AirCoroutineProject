package com.mumayank.aircoroutineproject.sub

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
        bgTask(ViewModelProvider(this)[AirViewModel::class.java])
    }

    private fun bgTask(airViewModel: AirViewModel) {
        AirCoroutines.execute(this, false, object: AirCoroutines.Callback {
            override suspend fun aOnTask(coroutineScope: CoroutineScope): Boolean {
                delay(1)
                return true
            }

            override fun bOnSuccess() {
                airViewModel.any = airViewModel.any + 1
                EventBus.getDefault().postSticky(OnIndexUpdatedEvent())
                bgTask(airViewModel)
            }

            override fun cOnError() {
                Toast.makeText(this@SubActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
