package com.example.foreground_practice.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.foreground_practice.service.SmartBandService

class SmartBandWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        SmartBandService.startService(applicationContext, "Foreground Service is running..")
        sleep(3000L)
        SmartBandService.stopService(applicationContext)

        return Result.success()
    }
}