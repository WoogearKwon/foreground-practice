package com.example.foreground_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart.setOnClickListener {
            ForegroundService.startService(this, "Foreground Service is running..")
        }

        buttonStop.setOnClickListener {
            ForegroundService.stopService(this)
        }
    }
}