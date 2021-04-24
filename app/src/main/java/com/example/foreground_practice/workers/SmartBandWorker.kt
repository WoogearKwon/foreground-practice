package com.example.foreground_practice.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.foreground_practice.helper.WorkerListener
import com.example.foreground_practice.service.SmartBandService

class SmartBandWorker(context: Context, params: WorkerParameters)
    : Worker(context, params), WorkerListener {

    override fun doWork(): Result {
        SmartBandService.startService(applicationContext, "Foreground Service is running..", this)
        Log.d("woogear", "WOOGEAR! THIS IS WORK ON BACKGROUND!")
        return Result.success()
    }

    override fun onWorkCompleted() {
        SmartBandService.stopService(applicationContext)
    }
}