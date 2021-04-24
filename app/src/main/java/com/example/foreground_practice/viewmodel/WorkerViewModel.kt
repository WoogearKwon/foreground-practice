package com.example.foreground_practice.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.foreground_practice.workers.PREF_RECORD_NUMBER
import com.example.foreground_practice.workers.SmartBandWorker
import com.example.foreground_practice.workers.WORK_MANAGER_TAG
import java.util.concurrent.TimeUnit

class WorkerViewModel(application: Application) : AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)
    val work = MutableLiveData<Int>()

    companion object{
        const val WORK_STARTED = 1
        const val WORK_FINISHED = 2
        const val WORK_CANCELED = 3
    }

    fun startWork() {
        val workRequest = PeriodicWorkRequest.Builder(
            SmartBandWorker::class.java,
            15,
            TimeUnit.MINUTES
        )
            .addTag(WORK_MANAGER_TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_MANAGER_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun stopWorkManager() {
        workManager.cancelAllWorkByTag(WORK_MANAGER_TAG)
        work.value = WORK_CANCELED
    }
}