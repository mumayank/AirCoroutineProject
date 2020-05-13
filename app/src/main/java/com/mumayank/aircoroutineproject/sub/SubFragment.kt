package com.mumayank.aircoroutineproject.sub

import android.app.Activity
import androidx.fragment.app.Fragment
import com.mumayank.aircoroutine.AirCoroutines
import com.mumayank.aircoroutineproject.R
import kotlinx.android.synthetic.main.sub_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class SubFragment : Fragment(R.layout.sub_fragment) {

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun handleSomethingElse(onIndexUpdatedEvent: OnIndexUpdatedEvent) {
        textView?.text =
            (AirCoroutines.getAirViewModelOfActivity(activity as Activity).any).toString()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

}