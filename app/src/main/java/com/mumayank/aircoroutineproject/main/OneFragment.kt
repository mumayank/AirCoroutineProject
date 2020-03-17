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
import kotlinx.android.synthetic.main.one_fragment.*
import kotlinx.coroutines.delay

class OneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.one_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateValue()
    }

    private fun updateValue() {
        AirCoroutine.execute(activity as Activity, object : AirCoroutine.Callback {

            override suspend fun doTaskInBg(viewModel: ViewModel): Boolean? {
                delay(100)
                return true
            }

            override fun isTaskTypeCalculation(): Boolean {
                return true
            }

            override fun onSuccess(viewModel: ViewModel) {
                textView?.text =
                    (MainActivity.value.toFloat() / 10.toFloat()).toInt()
                        .toString()
                updateValue()
            }

            override fun onFailure(viewModel: ViewModel) {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })

    }

}