package com.mumayank.aircoroutineproject.start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mumayank.aircoroutineproject.R
import com.mumayank.aircoroutineproject.main.MainActivity
import kotlinx.android.synthetic.main.start_activity.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)
        button.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }
}
