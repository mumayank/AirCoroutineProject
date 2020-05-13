package com.mumayank.aircoroutineproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mumayank.aircoroutineproject.sub.SubActivity
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        aButton.setOnClickListener {
            startActivity(Intent(this, SubActivity::class.java))
        }
    }

}
