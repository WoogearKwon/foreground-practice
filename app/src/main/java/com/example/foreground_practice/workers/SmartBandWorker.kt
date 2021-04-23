package com.example.foreground_practice.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.foreground_practice.service.SmartBandService

class SmartBandWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        SmartBandService.startService(applicationContext, "Foreground Service is running..")
        sleep(3000L)
        // TODO: write BLE login here

        SmartBandService.stopService(applicationContext)
        Log.d("woogear", "WOOGEAR! THIS IS WORK ON BACKGROUND!")
        val pref = applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        val number = pref.getInt(PREF_RECORD_NUMBER, 0)
        pref.edit().putInt(PREF_RECORD_NUMBER, number + 1).apply()

        return Result.success()
    }
}