package com.example.foreground_practice.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.foreground_practice.R
import com.example.foreground_practice.workers.PREF_KEY
import com.example.foreground_practice.workers.PREF_RECORD_NUMBER
import com.example.foreground_practice.workers.SmartBandWorker
import com.example.foreground_practice.workers.WORK_MANAGER_TAG
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val workManager = WorkManager.getInstance(this)
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

        tvSavedNumber.text = pref.getInt(PREF_RECORD_NUMBER, 0).toString()

        buttonStart.setOnClickListener {
//            SmartBandService.startService(this, "Foreground Service is running..")
            startWorkManager()
        }

        buttonStop.setOnClickListener {
//            SmartBandService.stopService(this)
            stopWorkManager()
        }
    }

    private fun startWorkManager() {

//        val workRequest = OneTimeWorkRequest.from(SmartBandWorker::class.java)
        val workRequest = PeriodicWorkRequest.Builder(
            SmartBandWorker::class.java,
            15,
            TimeUnit.MINUTES
        )
            .addTag(WORK_MANAGER_TAG)
            .build()

//        workManager.enqueue(workRequest)
        workManager.enqueueUniquePeriodicWork(
            WORK_MANAGER_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun stopWorkManager() {
        workManager.cancelAllWorkByTag(WORK_MANAGER_TAG)
        pref.edit().putInt(PREF_RECORD_NUMBER, 0).apply()
    }
}