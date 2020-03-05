package com.mumayank.aircoroutineproject.main

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.mumayank.aircoroutine.AirCoroutine
import com.mumayank.aircoroutineproject.R
import kotlinx.android.synthetic.main.two_fragment.*
import kotlinx.coroutines.delay

class TwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.two_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateValue()
    }

    private fun updateValue() {
        AirCoroutine.execute(
            AirCoroutine.getViewModel(
                activity as Activity,
                MainViewModel::class.java
            ), object : AirCoroutine.Callback {

                override suspend fun doTaskInBg(viewModel: ViewModel): AirCoroutine.TaskResult? {
                    delay(100)
                    (viewModel as MainViewModel).value++
                    return AirCoroutine.TaskResult.SUCCESS
                }

                override fun getTaskType(): AirCoroutine.TaskType? {
                    return AirCoroutine.TaskType.CALCULATIONS
                }

                override fun onSuccess(viewModel: ViewModel) {
                    textView?.text = ((viewModel as MainViewModel).value).toString()
                    updateValue()
                }

                override fun onFailure(viewModel: ViewModel) {
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }

            })
    }

}