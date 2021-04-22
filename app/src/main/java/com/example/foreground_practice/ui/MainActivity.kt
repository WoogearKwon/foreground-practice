package com.example.foreground_practice.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.foreground_practice.service.SmartBandService
import com.example.foreground_practice.R
import com.example.foreground_practice.workers.SmartBandWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart.setOnClickListener {
//            SmartBandService.startService(this, "Foreground Service is running..")
            startService()
        }

        buttonStop.setOnClickListener {
//            SmartBandService.stopService(this)
        }
    }

    private fun startService() {
        val workRequest = OneTimeWorkRequest.from(SmartBandWorker::class.java)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        workManager.enqueue(workRequest)
    }
}