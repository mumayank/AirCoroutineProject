package com.mumayank.aircoroutineproject.sub

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.mumayank.aircoroutine.AirViewModel
import com.mumayank.aircoroutineproject.R
import kotlinx.android.synthetic.main.sub_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class SubFragment : Fragment(R.layout.sub_fragment) {

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun handleSomethingElse(onIndexUpdatedEvent: OnIndexUpdatedEvent) {
        textView?.text =
            ViewModelProvider(activity as ViewModelStoreOwner)[AirViewModel::class.java].any.toString()
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