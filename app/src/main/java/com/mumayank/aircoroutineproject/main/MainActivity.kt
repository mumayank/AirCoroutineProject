package com.mumayank.aircoroutineproject.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mumayank.aircoroutineproject.R
import com.mumayank.aircoroutineproject.helper.FragmentHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        FragmentHelper.replaceFragmentInActivity(
            this,
            OneFragment(),
            false,
            R.id.topLayout
        )
        FragmentHelper.replaceFragmentInActivity(
            this,
            TwoFragment(),
            false,
            R.id.bottomLayout
        )
    }

}
